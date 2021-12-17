package com.example.plantsapp.di.module

import com.example.plantsapp.domain.workmanager.TasksWorkManager
import com.example.plantsapp.presentation.ui.worker.TasksWorkManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WorkModule {

    @Binds
    fun bindTasksWorkManager(tasksWorkManagerImpl: TasksWorkManagerImpl): TasksWorkManager
}
