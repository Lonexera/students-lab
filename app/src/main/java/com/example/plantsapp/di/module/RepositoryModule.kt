package com.example.plantsapp.di.module

import com.example.plantsapp.data.repository.RoomPlantsRepository
import com.example.plantsapp.data.repository.RoomTasksHistoryRepository
import com.example.plantsapp.data.repository.RoomTasksRepository
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPlantsRepository(repo: RoomPlantsRepository): PlantsRepository

    @Binds
    fun bindTasksRepository(repo: RoomTasksRepository): TasksRepository

    @Binds
    fun bindTasksHistoryRepository(repo: RoomTasksHistoryRepository): TasksHistoryRepository
}
