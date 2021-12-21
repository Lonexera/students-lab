package com.example.statisticsapp.presentation.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statisticsapp.domain.model.Plant
import com.example.statisticsapp.domain.usecase.GetPlantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getPlantsUseCase: GetPlantsUseCase
) : ViewModel() {

    private val _plants: MutableLiveData<List<Plant>> = MutableLiveData()
    val plants: LiveData<List<Plant>> get() = _plants

    init {
        viewModelScope.launch {
            _plants.value = getPlantsUseCase()
        }
    }
}
