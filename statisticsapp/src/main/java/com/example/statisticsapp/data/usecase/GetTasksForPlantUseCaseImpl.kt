package com.example.statisticsapp.data.usecase

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantstatscontract.PlantStatisticsContract
import com.example.statisticsapp.domain.usecase.GetTasksForPlantUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject


class GetTasksForPlantUseCaseImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : GetTasksForPlantUseCase {

    override suspend fun invoke(plant: Plant): List<Task> {
        return appContext.contentResolver.query(
            Uri.parse(PlantStatisticsContract.Tasks.CONTENT_URI),
            null,
            null,
            arrayOf(plant.name.value),
            null
        )
            ?.use { it.getTasks() } ?: emptyList()
    }

    private fun Cursor.getTasks(): List<Task> {
        val taskKeyIndex = getColumnIndex(PlantStatisticsContract.Tasks.FIELD_TASK_KEY)
        val taskFrequencyIndex = getColumnIndex(PlantStatisticsContract.Tasks.FIELD_FREQUENCY)
        val taskLastUpdateDateIndex =
            getColumnIndex(PlantStatisticsContract.Tasks.FIELD_LAST_UPDATE_DATE)

        return when (
            checkIfColumnNamesExists(
                taskKeyIndex,
                taskFrequencyIndex,
                taskLastUpdateDateIndex
            )
        ) {
            true -> {
                val tasks = mutableListOf<Task>()
                while (moveToNext()) {
                    tasks += getTask(taskKeyIndex, taskFrequencyIndex, taskLastUpdateDateIndex)
                }
                tasks
            }
            false -> emptyList()
        }
    }

    private fun Cursor.getTask(
        taskKeyIndex: Int,
        taskFrequencyIndex: Int,
        taskLastUpdateDateIndex: Int
    ): Task {
        return TaskKeys
            .getFromKey(getString(taskKeyIndex))
            .toTask(
                taskId = 0, // TODO remove id from Task model
                frequency = getInt(taskFrequencyIndex),
                lastUpdateDate = Date(getLong(taskLastUpdateDateIndex))
            )
    }

    private fun checkIfColumnNamesExists(
        taskKeyIndex: Int,
        taskFrequencyIndex: Int,
        taskLastUpdateDateIndex: Int
    ): Boolean {
        return taskKeyIndex >= 0 && taskFrequencyIndex >= 0 && taskLastUpdateDateIndex >= 0
    }
}
