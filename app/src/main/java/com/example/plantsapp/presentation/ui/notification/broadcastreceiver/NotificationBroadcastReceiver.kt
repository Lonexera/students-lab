package com.example.plantsapp.presentation.ui.notification.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import com.example.plantsapp.presentation.ui.notification.TaskNotificationManager
import com.example.plantsapp.presentation.ui.notification.utils.notificationId
import com.example.plantsapp.presentation.ui.notification.utils.taskFrequency
import com.example.plantsapp.presentation.ui.notification.utils.taskId
import com.example.plantsapp.presentation.ui.notification.utils.taskKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent?) = Unit
}

@AndroidEntryPoint
class NotificationBroadcastReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var completeTaskUseCase: CompleteTaskUseCase

    @Inject
    lateinit var taskNotificationManager: TaskNotificationManager

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        val pendingResult = goAsync()

        intent?.let {
            val task = TaskKeys.getFromKey(it.taskKey)
                .toTask(
                    taskId = it.taskId,
                    frequency = it.taskFrequency
                )

            GlobalScope.launch {
                completeTaskUseCase(task, Date())
                taskNotificationManager.cancelNotification(it.notificationId)
                pendingResult.finish()
            }
        }
    }
}
