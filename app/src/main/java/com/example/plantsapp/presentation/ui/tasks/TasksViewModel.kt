package com.example.plantsapp.presentation.ui.tasks

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
import com.example.plantsapp.domain.usecase.CompleteTaskUseCase
import com.example.plantsapp.domain.usecase.GetTasksForPlantAndDateUseCase
import com.example.plantsapp.presentation.core.Event
import com.example.plantsapp.presentation.model.TaskWithState
import com.example.plantsapp.presentation.ui.utils.isSameDay
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.Date

class TasksViewModel @AssistedInject constructor(
    @FirebaseQualifier private val plantsRepository: PlantsRepository,
    @FirebaseQualifier private val plantPhotosRepository: PlantPhotosRepository,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val getTasksForPlantAndDateUseCase: GetTasksForPlantAndDateUseCase,
    @Assisted private val date: Date
) : ViewModel() {

    private val _plantsWithTasks: MutableLiveData<List<Pair<Plant, List<TaskWithState>>>> =
        MutableLiveData()
    val plantsWithTasks: LiveData<List<Pair<Plant, List<TaskWithState>>>> = _plantsWithTasks

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _launchCamera: MutableLiveData<Event<Unit>> = MutableLiveData()
    val launchCamera: LiveData<Event<Unit>> = _launchCamera
    private var takingPhotoTaskWithPlant: Pair<Plant, Task>? = null

    init {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                fetchTasks()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onCompleteTaskClicked(plant: Plant, task: Task) {
        when (task) {
            is Task.TakingPhotoTask -> {
                takingPhotoTaskWithPlant = plant to task
                _launchCamera.value = Event(Unit)
            }
            else -> {
                viewModelScope.launch {
                    try {
                        _isLoading.value = true
                        completeTask(plant, task)
                    } finally {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun onImageCaptured(uri: Uri) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val (plant, task) = takingPhotoTaskWithPlant
                    ?: throw IllegalStateException("Cannot access stored plant and task for taking photo")

                plantPhotosRepository.savePhoto(plant, uri)
                completeTask(plant, task)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun completeTask(plant: Plant, task: Task) {
        completeTaskUseCase(plant, task, date)
        fetchTasks()
    }

    private suspend fun fetchTasks() {
        _plantsWithTasks.value =
            plantsRepository.fetchPlants()
                .map { plant ->
                    plant to
                            getTasksForPlantAndDateUseCase(plant, date)
                                .map { it.toTaskWithState() }
                }
                .filter { it.second.isNotEmpty() }
    }

    private fun Pair<Task, Boolean>.toTaskWithState(): TaskWithState {
        val (task, isCompleted) = this
        return TaskWithState(
            task = task,
            isCompleted = isCompleted,
            isCompletable = date.isSameDay(Date())
        )
    }
}
