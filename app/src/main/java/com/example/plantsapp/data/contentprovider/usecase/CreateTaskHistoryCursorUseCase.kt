package com.example.plantsapp.data.contentprovider.usecase

import android.database.MatrixCursor
import com.example.plantsapp.data.contentprovider.usecase.utils.getDefaultPlantWithName
import com.example.plantsapp.data.contentprovider.usecase.utils.getDefaultTaskWithKey
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantstatscontract.PlantStatisticsContract
import java.util.Date
import javax.inject.Inject

class CreateTaskHistoryCursorUseCase @Inject constructor(
    @FirebaseQualifier private val tasksHistoryRepository: TasksHistoryRepository
) {
    suspend operator fun invoke(plantName: String, taskKey: String): MatrixCursor {
        return MatrixCursor(
            arrayOf(PlantStatisticsContract.TaskHistory.FIELD_COMPLETION_DATE)
        )
            .apply {
                tasksHistoryRepository
                    .getTaskHistory(
                        plant = getDefaultPlantWithName(plantName),
                        task = getDefaultTaskWithKey(taskKey)
                    )
                    .forEach { putTaskCompletion(it) }
            }
    }

    private fun MatrixCursor.putTaskCompletion(completionDate: Date) {
        newRow()
            .add(PlantStatisticsContract.TaskHistory.FIELD_COMPLETION_DATE, completionDate)
    }
}
