package com.example.plantsapp.di.module.firebase

import com.example.plantsapp.data.firebase.repository.FirebasePlantsRepository
import com.example.plantsapp.data.firebase.repository.FirebaseTaskHistoryRepository
import com.example.plantsapp.data.firebase.repository.FirebaseTasksRepository
import com.example.plantsapp.data.firebase.repository.FirebaseUserRepository
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseRepositoryModule {

    @[Binds FirebaseQualifier]
    fun bindPlantsRepository(repo: FirebasePlantsRepository): PlantsRepository

    @[Binds FirebaseQualifier]
    fun bindTasksRepository(repo: FirebaseTasksRepository): TasksRepository

    @[Binds FirebaseQualifier]
    fun bindTasksHistoryRepository(repo: FirebaseTaskHistoryRepository): TasksHistoryRepository

    @[Binds FirebaseQualifier]
    fun bindUserRepository(repo: FirebaseUserRepository): UserRepository
}
