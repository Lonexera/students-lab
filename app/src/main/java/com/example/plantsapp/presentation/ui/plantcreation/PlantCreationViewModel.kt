package com.example.plantsapp.presentation.ui.plantcreation

import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.core.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class PlantCreationViewModel(
    private val repository: PlantsRepository
) : ViewModel() {

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    private val _selectedPicture: MutableLiveData<String> = MutableLiveData()
    val selectedPicture: LiveData<String> get() = _selectedPicture

    fun saveData(
        plantName: String,
        speciesName: String,
        wateringFrequency: String
    ) {
        viewModelScope.launch {
            Timber.d("Plant name: $plantName")
            Timber.d("Plant species name: $speciesName")
            Timber.d("Watering frequency in days: $wateringFrequency")

            repository.addPlant(
                Plant(
                    Plant.Name(plantName),
                    speciesName,
                    selectedPicture.value.orEmpty(),
                    wateringFrequency.toInt()
                )
            )

            _toNavigateBack.value = Event(Unit)
        }
    }

    fun onImageClicked(activityResultRegistry: ActivityResultRegistry) {
        val imagePicker = ImagePicker(activityResultRegistry) {
            _selectedPicture.value = it?.toString()
        }
        imagePicker.pickImage()
    }
}
