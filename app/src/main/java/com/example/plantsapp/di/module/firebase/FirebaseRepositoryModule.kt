@file:Suppress("WildcardImport", "NoWildcardImports")
package com.example.plantsapp.di.module.firebase

import com.example.plantsapp.data.firebase.repository.*
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.repository.*
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

    @[Binds FirebaseQualifier]
    fun bindPlantPhotosRepository(repo: FirestorePlantPhotosRepository): PlantPhotosRepository
}
