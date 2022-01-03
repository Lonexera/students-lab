package com.example.plantsapp.presentation.ui.tasksfordays

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StatisticsAppResolver @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val intent = Intent().apply {
        component = ComponentName(
            PACKAGE_NAME,
            ACTIVITY_CLASS_NAME
        )
    }

    fun isAppInstalled(): Boolean {
        return context.packageManager.resolveActivity(intent,0) != null
    }

    companion object {
        private const val PACKAGE_NAME = "com.example.statisticsapp"
        private const val ACTIVITY_CLASS_NAME = "com.example.statisticsapp.presentation.ui.MainActivity"
    }
}
