package com.example.plantsapp.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context
            .getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

    private const val PREFERENCES_FILE_KEY = "com.example.plantsApp.PREFERENCE_FILE_KEY"
}
