package com.example.plantsapp.presentation.ui.plantcreation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.R
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.SaveUriInStorageUseCase
import com.example.plantsapp.uicore.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.Date
import javax.inject.Inject
import kotlin.Exception

@Suppress("TooGenericExceptionCaught")
@HiltViewModel
class PlantCreationViewModel @Inject constructor(
    @FirebaseQualifier private val plantsRepository: PlantsRepository,
    @FirebaseQualifier private val tasksRepository: TasksRepository,
    private val validator: PlantCreationValidator,
    private val saveUriInStorageUseCase: SaveUriInStorageUseCase
) : ViewModel() {

    data class PlantTaskFrequencies(
        val wateringFrequency: Int?,
        val sprayingFrequency: Int?,
        val looseningFrequency: Int?,
        val takingPhotoFrequency: Int?
    )

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    private val _selectedPicture: MutableLiveData<Uri> = MutableLiveData()
    val selectedPicture: LiveData<Uri> get() = _selectedPicture

    private val _frequencies: MutableLiveData<PlantTaskFrequencies> = MutableLiveData(
        PlantTaskFrequencies(null, null, null, null)
    )
    val frequencies: LiveData<PlantTaskFrequencies> = _frequencies

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
                    try {
                        _isLoading.value = true
                        addPlant(
                            plantName,
                            speciesName,
                            selectedPicture.value,
                            frequencies.value!!
                        )
                    } catch (e: Exception) {
                        Timber.e(e)
                    } finally {
                        _isLoading.value = false
                    }
                }

                is PlantCreationValidator.ValidatorOutput.Error -> {
                    _invalidInput.value = validationResult.errorMessageRes
                }
            }
        }
    }

    fun onImageCaptured(uri: Uri) {
        _selectedPicture.value = uri
    }

    fun onImageSelected(uri: Uri) {
        try {
            val newSavedUri = saveUriInStorageUseCase.saveImage(uri)
            _selectedPicture.value = newSavedUri
        } catch (exception: IOException) {
            Timber.e(exception)
            _invalidInput.value = R.string.error_copying_image
        }
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

    fun onTakingPhotoFrequencySelected(frequency: Int) {
        _frequencies.value = _frequencies.value?.copy(takingPhotoFrequency = frequency)
    }

    @Suppress("LongParameterList")
    private suspend fun addPlant(
        plantName: String,
        speciesName: String,
        plantPicture: Uri?,
        frequencies: PlantTaskFrequencies
    ) {
        try {
            val createdPlant = Plant(
                Plant.Name(plantName),
                speciesName,
                plantPicture?.toString()
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
        frequencies: PlantTaskFrequencies
    ) {
        val todayDate = Date()
        tasksRepository.addTasks(
            plant,
            listOf(
                Task.WateringTask(frequencies.wateringFrequency!!, todayDate),
                Task.SprayingTask(frequencies.sprayingFrequency!!, todayDate),
                Task.LooseningTask(frequencies.looseningFrequency!!, todayDate),
                Task.TakingPhotoTask(frequencies.takingPhotoFrequency!!, todayDate)
            )
        )
    }

    companion object {
        private const val MIN_WATERING_FREQUENCY = 1
        private const val MAX_WATERING_FREQUENCY = 31
    }
}
