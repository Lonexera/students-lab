package com.example.plantsapp.presentation.ui.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.core.Event

class PlantsViewModel(
    private val repository: PlantsRepository
) : ViewModel() {

    val plants: LiveData<List<Plant>> = MutableLiveData(repository.allPlants())
    private val _clickedPlant: MutableLiveData<Event<Plant>> = MutableLiveData()
    val clickedPlant: LiveData<Event<Plant>> get() = _clickedPlant
    private val _toCreation: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toCreation: LiveData<Event<Unit>> get() = _toCreation

    fun onPlantClicked(plant: Plant) {
        _clickedPlant.value = Event(plant)
    }

    fun onAddPlantClicked() {
        _toCreation.value = Event(Unit)
    }
}
