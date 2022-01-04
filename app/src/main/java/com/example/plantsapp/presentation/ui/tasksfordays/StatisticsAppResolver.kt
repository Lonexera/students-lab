package com.example.plantsapp.presentation.ui.tasksfordays

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.IllegalStateException

class StatisticsAppResolver @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val intent = context.packageManager.getLaunchIntentForPackage(PACKAGE_NAME)

    fun isAppInstalled(): Boolean {
        return intent != null
    }

    fun requireIntent() = intent ?: throw IllegalStateException("Statistics App is not installed")

    companion object {
        private const val PACKAGE_NAME = "com.example.statisticsapp"
    }
}
