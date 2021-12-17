package com.example.plantsapp.di.module

import android.content.Context
import com.example.plantsapp.presentation.ui.worker.TasksWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkModule {

    @Provides
    @Singleton
    fun provideTasksWorkManager(
        @ApplicationContext context: Context
    ): TasksWorkManager = TasksWorkManager(context)
}
