package com.example.plantsapp.di.module

import android.content.Context
import com.example.plantsapp.data.RoomPlantsDatabase
import com.example.plantsapp.data.dao.RoomPlantWithTasksDao
import com.example.plantsapp.data.dao.RoomPlantsDao
import com.example.plantsapp.data.repository.RoomPlantsRepository
import com.example.plantsapp.data.repository.RoomTasksRepository
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksRepository
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
    ): RoomPlantWithTasksDao {
        return database.plantsWithTasksDao()
    }

    @Provides
    fun providePlantsRepo(repo: RoomPlantsRepository): PlantsRepository {
        return repo
    }

    @Provides
    fun provideTasksRepo(repo: RoomTasksRepository): TasksRepository {
        return repo
    }
}
