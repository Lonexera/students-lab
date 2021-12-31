package com.example.statisticsapp.presentation.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statisticsapp.domain.usecase.GetPlantsUseCase
import com.example.statisticsapp.domain.usecase.GetTaskCompletionsAmountUseCase
import com.example.statisticsapp.domain.usecase.GetTasksForPlantUseCase
import com.example.statisticsapp.presentation.model.PlantStatisticsInfo
import com.example.statisticsapp.presentation.model.TaskStatisticsInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val getTasksForPlantUseCase: GetTasksForPlantUseCase,
    private val getTaskCompletionsAmountUseCase: GetTaskCompletionsAmountUseCase
) : ViewModel() {

    private val _plantsWithTasksStats: MutableLiveData<List<PlantStatisticsInfo>> =
        MutableLiveData()
    val plantsWithTasksStatistics: LiveData<List<PlantStatisticsInfo>> get() = _plantsWithTasksStats

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _plantsWithTasksStats.value = fetchPlantsStatisticsInfo()
            } catch (e: Exception) {
                Timber.e(e)
                // TODO process loading error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchPlantsStatisticsInfo(): List<PlantStatisticsInfo> {
        return getPlantsUseCase().map { plant ->
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
