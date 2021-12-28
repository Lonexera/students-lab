package com.example.statisticsapp.di

import com.example.statisticsapp.data.usecase.GetPlantsUseCaseImpl
import com.example.statisticsapp.data.usecase.GetTaskCompletionsAmountUseCaseImpl
import com.example.statisticsapp.data.usecase.GetTasksForPlantUseCaseImpl
import com.example.statisticsapp.domain.usecase.GetPlantsUseCase
import com.example.statisticsapp.domain.usecase.GetTaskCompletionsAmountUseCase
import com.example.statisticsapp.domain.usecase.GetTasksForPlantUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetPlantsUseCase(getPlantsUseCaseImpl: GetPlantsUseCaseImpl): GetPlantsUseCase

    @Binds
    fun bindGetTasksUseCase(
        getTasksForPlantUseCaseImpl: GetTasksForPlantUseCaseImpl
    ): GetTasksForPlantUseCase

    @Binds
    fun bindGetTaskCompletionNumberUseCase(
        getTaskCompletionsNumberUseCaseImpl: GetTaskCompletionsAmountUseCaseImpl
    ): GetTaskCompletionsAmountUseCase
}
