@file:Suppress("WildcardImport", "NoWildcardImports")
package com.example.plantsapp.di.module

import com.example.plantsapp.data.usecase.*
import com.example.plantsapp.domain.usecase.*
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

    @Binds
    fun bindSignOutUseCase(
        signOutUseCaseImpl: SignOutUseCaseImpl
    ): SignOutUseCase
}
