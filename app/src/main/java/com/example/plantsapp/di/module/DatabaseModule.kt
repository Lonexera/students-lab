package com.example.plantsapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.plantsapp.BuildConfig
import com.example.plantsapp.data.room.RoomPlantsDatabase
import com.example.plantsapp.data.room.dao.RoomTasksDao
import com.example.plantsapp.data.room.dao.RoomPlantsDao
import com.example.plantsapp.data.room.dao.RoomTaskHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RoomPlantsDatabase {
        return Room.databaseBuilder(
            context,
            RoomPlantsDatabase::class.java,
            "plants_database"
        )
            .apply {
                if (BuildConfig.DEBUG) {
                    createFromAsset("database/plants_database.db")
                }
            }
            .build()
    }

    @Provides
    fun providePlantsDao(
        database: RoomPlantsDatabase
    ): RoomPlantsDao {
        return database.plantDao()
    }

    @Provides
    fun providePlantsWithTasksDao(
        database: RoomPlantsDatabase
    ): RoomTasksDao {
        return database.plantsWithTasksDao()
    }

    @Provides
    fun provideTaskHistoryDao(
        database: RoomPlantsDatabase
    ): RoomTaskHistoryDao {
        return database.taskHistoryDao()
    }
}
