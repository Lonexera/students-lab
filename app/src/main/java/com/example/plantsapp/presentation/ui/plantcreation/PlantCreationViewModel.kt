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
import timber.log.Timber
import kotlin.Exception

class PlantCreationViewModel(
    private val repository: PlantsRepository,
    private val validator: PlantCreationValidator
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

            val validationResult = validator.validate(
                plantName,
                speciesName,
                wateringSelectedFrequency.value
            )

            when (validationResult) {
                is PlantCreationValidator.ValidatorOutput.Success -> {
                    addPlant(
                        plantName,
                        speciesName,
                        selectedPicture.value,
                        wateringSelectedFrequency.value!!
                    )
                }

                is PlantCreationValidator.ValidatorOutput.Error -> {
                    _invalidInput.value = validationResult.errorMessageRes
                }
            }
        }
    }

    fun onImageSelected(uri: Uri) {
        _selectedPicture.value = uri
    }

    fun onWateringFrequencySelected(frequency: Int) {
        _wateringSelectedFrequency.value = frequency
    }

    @Suppress("TooGenericExceptionCaught")
    private suspend fun addPlant(
        plantName: String,
        speciesName: String,
        plantPicture: Uri?,
        wateringFrequency: Int
    ) {
        try {
            repository.addPlant(
                Plant(
                    Plant.Name(plantName),
                    speciesName,
                    plantPicture,
                    wateringFrequency
                )
            )
            _toNavigateBack.value = Event(Unit)
        } catch (e: Exception) {
            Timber.e(e)
            _invalidInput.value = R.string.error_indistinctive_name
        }
    }

    companion object {
        private const val MIN_WATERING_FREQUENCY = 1
        private const val MAX_WATERING_FREQUENCY = 31
    }
}
