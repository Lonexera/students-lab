package com.example.plantsapp.presentation.ui.plantdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.domain.model.Plant

class PlantDetailViewModel : ViewModel() {

    private val _plant: MutableLiveData<Plant> = MutableLiveData()
    val plant: LiveData<Plant> get() = _plant

    fun setPlant(plant: Plant) {
        _plant.value = plant
    }
}
