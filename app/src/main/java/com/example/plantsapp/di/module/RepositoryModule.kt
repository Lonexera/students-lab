package com.example.plantsapp.di.module

import com.example.plantsapp.data.firebase.repository.FirebasePlantsRepository
import com.example.plantsapp.data.firebase.repository.FirebaseTaskHistoryRepository
import com.example.plantsapp.data.firebase.repository.FirebaseTasksRepository
import com.example.plantsapp.data.room.repository.RoomPlantsRepository
import com.example.plantsapp.data.room.repository.RoomTasksHistoryRepository
import com.example.plantsapp.data.room.repository.RoomTasksRepository
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

    @FirebaseQualifier
    @Binds
    fun bindFirebasePlantsRepository(repo: FirebasePlantsRepository): PlantsRepository

    @FirebaseQualifier
    @Binds
    fun bindFirebaseTasksRepository(repo: FirebaseTasksRepository): TasksRepository

    @FirebaseQualifier
    @Binds
    fun bindFirebaseTasksHistoryRepository(repo: FirebaseTaskHistoryRepository): TasksHistoryRepository

    @RoomQualifier
    @Binds
    fun bindRoomPlantsRepository(repo: RoomPlantsRepository): PlantsRepository

    @RoomQualifier
    @Binds
    fun bindRoomTasksRepository(repo: RoomTasksRepository): TasksRepository

    @RoomQualifier
    @Binds
    fun bindRoomTasksHistoryRepository(repo: RoomTasksHistoryRepository): TasksHistoryRepository
}
