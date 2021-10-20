package com.example.plantsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [RoomPlant::class], version = 1)
abstract class RoomPlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): RoomPlantsDao

    companion object {

        @Volatile
        private var INSTANCE: RoomPlantsDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): RoomPlantsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    RoomPlantsDatabase::class.java,
                    "plants_database"
                )
                    .addCallback(PlantsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class PlantsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.plantDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(plantsDao: RoomPlantsDao) {
            val twoDays: Int = 2
            val plantPicture = "https://www.vippng.com/png/detail/41-414674_house-plant-png.png"

            val listOfPlants = mutableListOf(
            RoomPlant("Bob", "Succulent", plantPicture, twoDays),
            RoomPlant("Marley", "Spath plant", plantPicture, twoDays),
            RoomPlant("John", "Cacti", plantPicture, twoDays),
            RoomPlant("Casey", "Tillandsia", plantPicture, twoDays),
            RoomPlant("Robert", "Succulent", plantPicture, twoDays)
            )

            listOfPlants.forEach {
                plantsDao.insert(it)
            }
        }
    }
}
