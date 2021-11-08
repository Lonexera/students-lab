package com.example.plantsapp.presentation.ui.plantcreation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.R
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.presentation.core.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.Exception

class PlantCreationViewModel(
    private val plantsRepository: PlantsRepository,
    private val tasksRepository: TasksRepository,
    private val validator: PlantCreationValidator
) : ViewModel() {

    data class Frequency(
        val wateringFrequency: Int?,
        val sprayingFrequency: Int?,
        val looseningFrequency: Int?
    )

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    private val _selectedPicture: MutableLiveData<Uri> = MutableLiveData()
    val selectedPicture: LiveData<Uri> get() = _selectedPicture

    private val _frequencies: MutableLiveData<Frequency> = MutableLiveData(
        Frequency(null, null, null)
    )
    val frequencies: LiveData<Frequency> = _frequencies

    val frequencyValues: LiveData<List<Int>> =
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
                frequencies.value!!
            )

            when (validationResult) {
                is PlantCreationValidator.ValidatorOutput.Success -> {
                    addPlant(
                        plantName,
                        speciesName,
                        selectedPicture.value,
                        frequencies.value!!
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
        _frequencies.value = _frequencies.value?.copy(wateringFrequency = frequency)
    }

    fun onSprayingFrequencySelected(frequency: Int) {
        _frequencies.value = _frequencies.value?.copy(sprayingFrequency = frequency)
    }

    fun onLooseningFrequencySelected(frequency: Int) {
        _frequencies.value = _frequencies.value?.copy(looseningFrequency = frequency)
    }

    @Suppress(
        "TooGenericExceptionCaught",
        "LongParameterList"
    )
    private suspend fun addPlant(
        plantName: String,
        speciesName: String,
        plantPicture: Uri?,
        frequencies: Frequency
    ) {
        try {
            val createdPlant = Plant(
                Plant.Name(plantName),
                speciesName,
                plantPicture
            )

            plantsRepository.addPlant(createdPlant)
            addTasks(createdPlant, frequencies)

            _toNavigateBack.value = Event(Unit)
        } catch (e: Exception) {
            Timber.e(e)
            _invalidInput.value = R.string.error_indistinctive_name
        }
    }

    private suspend fun addTasks(
        plant: Plant,
        frequencies: Frequency
    ) {
        tasksRepository.addTasks(
            plant,
            listOf(
                Task.WateringTask(
                    plant.name.value,
                    plant.plantPicture,
                    frequencies.wateringFrequency!!
                ),
                Task.SprayingTask(
                    plant.name.value,
                    plant.plantPicture,
                    frequencies.sprayingFrequency!!
                ),
                Task.LooseningTask(
                    plant.name.value,
                    plant.plantPicture,
                    frequencies.looseningFrequency!!
                )
            )
        )
    }

    companion object {
        private const val MIN_WATERING_FREQUENCY = 1
        private const val MAX_WATERING_FREQUENCY = 31
    }
}
