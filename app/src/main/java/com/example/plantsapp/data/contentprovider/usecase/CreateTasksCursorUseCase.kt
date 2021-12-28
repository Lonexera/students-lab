package com.example.plantsapp.data.contentprovider.usecase

import android.database.MatrixCursor
import com.example.plantsapp.data.contentprovider.usecase.utils.getDefaultPlantWithName
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantstatscontract.PlantStatisticsContract
import javax.inject.Inject

class CreateTasksCursorUseCase @Inject constructor(
    @FirebaseQualifier private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(plantName: String): MatrixCursor {
        return MatrixCursor(
            arrayOf(
                PlantStatisticsContract.Tasks.FIELD_TASK_KEY,
                PlantStatisticsContract.Tasks.FIELD_FREQUENCY,
                PlantStatisticsContract.Tasks.FIELD_LAST_UPDATE_DATE
            )
        )
            .apply {
                tasksRepository
                    .getTasksForPlant(plant = getDefaultPlantWithName(plantName))
                    .forEach { putTask(it) }
            }
    }

    private fun MatrixCursor.putTask(task: Task) {
        newRow()
            .add(PlantStatisticsContract.Tasks.FIELD_TASK_KEY, TaskKeys.from(task).key)
            .add(PlantStatisticsContract.Tasks.FIELD_FREQUENCY, task.frequency)
            .add(PlantStatisticsContract.Tasks.FIELD_LAST_UPDATE_DATE, task.lastUpdateDate)
    }
}
