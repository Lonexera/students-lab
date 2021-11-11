package com.example.plantsapp.presentation.ui.plantdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.core.Event
import kotlinx.coroutines.launch

class PlantDetailViewModel(
    private val repository: PlantsRepository,
    private val plantName: Plant.Name
) : ViewModel() {

    private val _plant: MutableLiveData<Plant> = MutableLiveData()
    val plant: LiveData<Plant> get() = _plant

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    init {
        viewModelScope.launch {
            _plant.value = repository.getPlantByName(plantName)
        }
    }

    fun onDelete(plant: Plant) {
        viewModelScope.launch {
            repository.deletePlant(plant)

            _toNavigateBack.value = Event(Unit)
        }
    }
}
