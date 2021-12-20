package com.example.plantsapp.domain.workmanager

import java.util.Calendar

interface TasksWorkManager {
    fun startWork(startDate: Calendar)
    fun cancelAllWork()
}
