package com.example.statisticsapp.presentation.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.statisticsapp.domain.usecase.GetPlantsUseCase
import com.example.statisticsapp.domain.usecase.GetTaskCompletionsAmountUseCase
import com.example.statisticsapp.domain.usecase.GetTasksForPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val getTasksForPlantUseCase: GetTasksForPlantUseCase,
    private val getTaskCompletionsAmountUseCase: GetTaskCompletionsAmountUseCase
) : ViewModel() {

    data class PlantStatisticsInfo(
        val plant: Plant,
        val tasks: List<TaskStatisticsInfo>
    )

    data class TaskStatisticsInfo(
        val task: Task,
        val amount: Int
    )

    private val _plantsWithTasksStats: MutableLiveData<List<PlantStatisticsInfo>> =
        MutableLiveData()
    val plantsWithTasksStatistics: LiveData<List<PlantStatisticsInfo>> get() = _plantsWithTasksStats

    // TODO add loading here
    init {
        viewModelScope.launch {
            _plantsWithTasksStats.value = getPlantsUseCase().map { plant ->
                val tasks = getTasksForPlantUseCase(plant).map { task ->
                    TaskStatisticsInfo(
                        task = task,
                        amount = getTaskCompletionsAmountUseCase(plant, task)
                    )
                }

                PlantStatisticsInfo(plant, tasks)
            }
        }
    }
}
