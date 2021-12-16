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
import java.util.Date

class PlantDetailViewModel @AssistedInject constructor(
    @FirebaseQualifier private val plantsRepository: PlantsRepository,
    @FirebaseQualifier private val tasksRepository: TasksRepository,
    @FirebaseQualifier private val plantPhotosRepository: PlantPhotosRepository,
    @Assisted plantName: String
) : ViewModel() {

    private val _plant: MutableLiveData<Plant> = MutableLiveData()
    val plant: LiveData<Plant> get() = _plant
    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val tasks: LiveData<List<Task>> get() = _tasks
    private val _plantPhotos: MutableLiveData<List<Pair<Uri, Date>>> = MutableLiveData()
    val plantPhotos: LiveData<List<Pair<Uri, Date>>> get() = _plantPhotos

    private val _toNavigateBack: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toNavigateBack: LiveData<Event<Unit>> get() = _toNavigateBack

    init {
        viewModelScope.launch {
            plantsRepository.getPlantByName(Plant.Name(plantName)).run {
                _plant.value = this
                _tasks.value = tasksRepository.getTasksForPlant(this)
                _plantPhotos.value = plantPhotosRepository
                    .getPlantPhotos(this)
                    .sortedByDescending { (_, creationDate) -> creationDate.time }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            plantsRepository.deletePlant(plant.value!!)

            _toNavigateBack.value = Event(Unit)
        }
    }
}
