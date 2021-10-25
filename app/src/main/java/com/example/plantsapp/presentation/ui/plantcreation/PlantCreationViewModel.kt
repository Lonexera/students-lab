package com.example.plantsapp.presentation.ui.plantcreation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.core.Event
import kotlinx.coroutines.launch

class PlantCreationViewModel(
    private val repository: PlantsRepository
) : ViewModel() {

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    private val _selectedPicture: MutableLiveData<Uri> = MutableLiveData()
    val selectedPicture: LiveData<Uri> get() = _selectedPicture

    private val _wateringSelectedFrequency: MutableLiveData<Int> = MutableLiveData()
    val wateringSelectedFrequency: LiveData<Int> get() = _wateringSelectedFrequency

    val wateringFrequencyValues: LiveData<List<Int>> =
        MutableLiveData((MIN_WATERING_FREQUENCY..MAX_WATERING_FREQUENCY).toList())

    private val _invalidInput: MutableLiveData<Int> = MutableLiveData()
    val invalidInput: LiveData<Int> get() = _invalidInput

    fun saveData(
        plantName: String,
        speciesName: String
    ) {
        viewModelScope.launch {

            if (checkInput(plantName, speciesName)) {
                repository.addPlant(
                    Plant(
                        Plant.Name(plantName),
                        speciesName,
                        selectedPicture.value,
                        wateringSelectedFrequency.value!!
                    )
                )

                _toNavigateBack.value = Event(Unit)
            }
        }
    }

    fun onImageSelected(uri: Uri) {
        _selectedPicture.value = uri
    }

    fun onWateringFrequencySelected(frequency: Int) {
        _wateringSelectedFrequency.value = frequency
    }

    private suspend fun checkInput(
        plantName: String,
        speciesName: String
    ): Boolean {
        return when {
            plantName.isBlank() -> {
                _invalidInput.value = R.string.error_invalid_name
                false
            }
            repository.checkIfPlantNameIsInDb(Plant.Name(plantName)) -> {
                _invalidInput.value = R.string.error_indistinctive_name
                false
            }
            speciesName.isBlank() -> {
                _invalidInput.value = R.string.error_invalid_species
                false
            }
            wateringSelectedFrequency.value == null -> {
                _invalidInput.value = R.string.error_invalid_watering_frequency
                false
            }
            else -> true
        }
    }

    companion object {
        private const val MIN_WATERING_FREQUENCY = 1
        private const val MAX_WATERING_FREQUENCY = 31
    }
}
