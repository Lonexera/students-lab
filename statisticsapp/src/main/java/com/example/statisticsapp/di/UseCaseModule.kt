package com.example.statisticsapp.di

import com.example.statisticsapp.data.usecase.GetPlantsUseCaseImpl
import com.example.statisticsapp.domain.usecase.GetPlantsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetPlantsUseCase(getPlantsUseCaseImpl: GetPlantsUseCaseImpl): GetPlantsUseCase
}
