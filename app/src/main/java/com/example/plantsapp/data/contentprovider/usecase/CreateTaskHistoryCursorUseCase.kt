package com.example.plantsapp.data.contentprovider.usecase

import android.database.MatrixCursor
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantstatscontract.PlantStatisticsContract
import java.util.Date
import javax.inject.Inject

class CreateTaskHistoryCursorUseCase @Inject constructor(
    @FirebaseQualifier private val tasksHistoryRepository: TasksHistoryRepository
) {
    suspend operator fun invoke(plant: Plant, task: Task): MatrixCursor {
        return MatrixCursor(
            arrayOf(PlantStatisticsContract.TaskHistory.FIELD_COMPLETION_DATE)
        )
            .apply {
                tasksHistoryRepository
                    .getTaskCompletionDates(plant, task)
                    .forEach { putTaskCompletion(it) }
            }
    }

    private fun MatrixCursor.putTaskCompletion(completionDate: Date) {
        newRow()
            .add(PlantStatisticsContract.TaskHistory.FIELD_COMPLETION_DATE, completionDate)
    }
}
