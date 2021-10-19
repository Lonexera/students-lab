package com.example.plantsapp.presentation.ui.plantdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import kotlinx.coroutines.launch

class PlantDetailViewModel(
    private val repository: PlantsRepository,
    private val plantName: String
) : ViewModel() {

    private val _plant: MutableLiveData<Plant> = MutableLiveData()
    val plant: LiveData<Plant> get() = _plant

    init {
        viewModelScope.launch {
            _plant.value = repository.getPlantByName(plantName)
        }
    }
}
