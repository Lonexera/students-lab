package com.example.plantsapp.di.module

import com.example.plantsapp.data.usecase.AuthUseCaseImpl
import com.example.plantsapp.data.usecase.CompleteTaskUseCaseImpl
import com.example.plantsapp.data.usecase.GetTasksForPlantAndDateUseCaseImpl
import com.example.plantsapp.data.usecase.SaveUriInStorageUseCaseImpl
import com.example.plantsapp.domain.usecase.AuthUseCase
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import com.example.plantsapp.domain.usecase.GetTasksForPlantAndDateUseCase
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
    fun bindGetTasksForPlantAndDateUseCase(
        getTasksForPlantAndDateUseCaseImpl: GetTasksForPlantAndDateUseCaseImpl
    ): GetTasksForPlantAndDateUseCase

    @Binds
    fun bindAuthUseCase(
        authUseCaseImpl: AuthUseCaseImpl
    ): AuthUseCase
}
