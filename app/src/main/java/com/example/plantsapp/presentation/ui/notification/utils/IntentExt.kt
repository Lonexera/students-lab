package com.example.plantsapp.presentation.ui.notification.utils

import android.content.Intent

var Intent.taskId: Int
    get() = getIntExtra(EXTRA_TASK_ID, 0)
    set(taskId) {
        putExtra(EXTRA_TASK_ID, taskId)
    }

var Intent.taskFrequency: Int
    get() = getIntExtra(EXTRA_TASK_FREQUENCY, 0)
    set(frequency) {
        putExtra(EXTRA_TASK_FREQUENCY, frequency)
    }

var Intent.taskKey: String
    get() = getStringExtra(EXTRA_TASK_KEY)
        ?: error("Task Key Was Not Passed!")
    set(taskKey) {
        putExtra(EXTRA_TASK_KEY, taskKey)
    }

var Intent.notificationId: Int
    get() = getIntExtra(EXTRA_NOTIFICATION_ID, 0)
    set(notificationId) {
        putExtra(EXTRA_NOTIFICATION_ID, notificationId)
    }

private const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
private const val EXTRA_TASK_ID = "EXTRA_TASK_ID"
private const val EXTRA_TASK_KEY = "EXTRA_TASK_KEY"
private const val EXTRA_TASK_FREQUENCY = "EXTRA_FREQUENCY"
