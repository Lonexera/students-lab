package com.example.plantsapp.di.module

import com.example.plantsapp.data.usecase.CompleteTaskUseCaseImpl
import com.example.plantsapp.data.usecase.GetPlantsWithTasksForDateUseCaseImpl
import com.example.plantsapp.data.usecase.SaveUriInStorageUseCaseImpl
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import com.example.plantsapp.domain.usecase.GetPlantsWithTasksForDateUseCase
import com.example.plantsapp.domain.usecase.SaveUriInStorageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindSaveUriInStorageUseCase(
        saveUriInStorageUseCaseImpl: SaveUriInStorageUseCaseImpl
    ): SaveUriInStorageUseCase

    @Binds
    fun bindCompleteTaskUseCase(
        completeTaskUseCaseImpl: CompleteTaskUseCaseImpl
    ): CompleteTaskUseCase

    @Binds
    fun bindGetPlantsWithTasksForDateUseCase(
        getPlantsWithTasksForDateUseCaseImpl: GetPlantsWithTasksForDateUseCaseImpl
    ): GetPlantsWithTasksForDateUseCase
}
