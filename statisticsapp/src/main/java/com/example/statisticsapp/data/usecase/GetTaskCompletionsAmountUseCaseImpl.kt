package com.example.statisticsapp.data.usecase

import android.content.Context
import android.net.Uri
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantstatscontract.PlantStatisticsContract
import com.example.statisticsapp.domain.usecase.GetTaskCompletionsAmountUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetTaskCompletionsAmountUseCaseImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : GetTaskCompletionsAmountUseCase {

    override suspend fun invoke(plant: Plant, task: Task): Int {
        return appContext.contentResolver.query(
            Uri.parse(PlantStatisticsContract.TaskHistory.CONTENT_URI),
            null,
            null,
            arrayOf(plant.name.value, TaskKeys.from(task).key),
            null
        )
            ?.use{ it.count } ?: throw NoSuchElementException("Task History data is not found (null cursor)")
    }
}
