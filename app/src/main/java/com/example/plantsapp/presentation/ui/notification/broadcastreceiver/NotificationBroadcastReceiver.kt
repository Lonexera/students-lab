package com.example.plantsapp.presentation.ui.notification.broadcastreceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import com.example.plantsapp.presentation.ui.notification.utils.notificationId
import com.example.plantsapp.presentation.ui.notification.utils.taskFrequency
import com.example.plantsapp.presentation.ui.notification.utils.taskId
import com.example.plantsapp.presentation.ui.notification.utils.taskKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
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

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.let {
            val task = TaskKeys.getFromKey(it.taskKey)
                .toTask(
                    taskId = it.taskId,
                    frequency = it.taskFrequency
                )

            runBlocking {
                completeTaskUseCase(task, Date())
                cancelNotification(context, it.notificationId)
            }
        }
    }

    private fun cancelNotification(context: Context, notificationId: Int) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .cancel(notificationId)
    }
}
