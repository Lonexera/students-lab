package com.example.statisticsapp.data.usecase

import android.content.Context
import android.net.Uri
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantstatscontract.PlantStatisticsContract
import com.example.statisticsapp.domain.usecase.GetTaskCompletionsAmountUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTaskCompletionsAmountUseCaseImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : GetTaskCompletionsAmountUseCase {

    override suspend fun invoke(plant: Plant, task: Task): Int =
        withContext(Dispatchers.IO) {
            appContext.contentResolver.query(
                Uri.parse(PlantStatisticsContract.TaskHistory.CONTENT_URI),
                null,
                null,
                // TODO Think how to extract selectionArgs creation and parsing
                PlantStatisticsContract.SelectionArgs.putPlantAndTaskInArgs(plant, task),
                null
            )
                ?.use { it.count }
                ?: throw NoSuchElementException("Task History data is not found (null cursor)")
        }
}
