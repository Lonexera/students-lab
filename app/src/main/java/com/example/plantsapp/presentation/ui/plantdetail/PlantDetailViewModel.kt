package com.example.plantsapp.presentation.ui.plantdetail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.PlantPhotosRepository
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.presentation.core.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date

class PlantDetailViewModel @AssistedInject constructor(
    @FirebaseQualifier private val plantsRepository: PlantsRepository,
    @FirebaseQualifier private val tasksRepository: TasksRepository,
    @FirebaseQualifier private val plantPhotosRepository: PlantPhotosRepository,
    @Assisted plantName: String
) : ViewModel() {

    val appBarTitle: LiveData<String> = MutableLiveData(plantName)

    private val _plant: MutableLiveData<Plant> = MutableLiveData()
    val plant: LiveData<Plant> get() = _plant
    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val tasks: LiveData<List<Task>> get() = _tasks
    private val _plantPhotos: MutableLiveData<List<Pair<Uri, Date>>> = MutableLiveData()
    val plantPhotos: LiveData<List<Pair<Uri, Date>>> get() = _plantPhotos

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    init {
        @Suppress("TooGenericExceptionCaught")
        viewModelScope.launch {
            try {
                _isLoading.value = true

                plantsRepository.getPlantByName(Plant.Name(plantName)).run {
                    _plant.value = this
                    _tasks.value = tasksRepository.getTasksForPlant(this)
                    _plantPhotos.value = plantPhotosRepository
                        .getPlantPhotos(this)
                        .sortedByDescending { (_, creationDate) -> creationDate.time }
                }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun onDelete() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                plantsRepository.deletePlant(plant.value!!)
                _toNavigateBack.value = Event(Unit)
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
