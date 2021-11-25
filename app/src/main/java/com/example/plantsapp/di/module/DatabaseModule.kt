package com.example.plantsapp.di.module

import android.content.Context
import com.example.plantsapp.data.RoomPlantsDatabase
import com.example.plantsapp.data.dao.RoomTasksDao
import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.dao.RoomTaskHistoryDao
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
        return RoomPlantsDatabase.getInstance(context = context)
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
