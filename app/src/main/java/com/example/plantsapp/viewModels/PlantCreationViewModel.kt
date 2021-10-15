package com.example.plantsapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.utils.Event
import timber.log.Timber

class PlantCreationViewModel : ViewModel() {

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    fun saveData(plantName: String, speciesName: String, wateringFrequency: String) {
        Timber.d("Plant name: $plantName")
        Timber.d("Plant species name: $speciesName")
        Timber.d("Watering frequency in days: $wateringFrequency")

        _toNavigateBack.value = Event(Unit)
    }
}
