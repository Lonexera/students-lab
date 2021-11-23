package com.example.plantsapp.presentation.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskWithState
import com.example.plantsapp.domain.repository.TasksRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.Date

class TasksViewModel @AssistedInject constructor(
    private val repository: TasksRepository,
    @Assisted private val date: Date
) : ViewModel() {

    private val _plantsWithTasks: MutableLiveData<List<Pair<Plant, List<TaskWithState>>>> =
        MutableLiveData()
    val plantsWithTasks: LiveData<List<Pair<Plant, List<TaskWithState>>>> = _plantsWithTasks

    init {
        viewModelScope.launch {
            fetchTasks()
        }
    }

    // TODO maybe replace Date() with midnight time date
    fun onCompleteTaskClicked(task: Task) {
        viewModelScope.launch {
            repository.completeTask(task, date)
            fetchTasks()
        }
    }

    private suspend fun fetchTasks() {
        _plantsWithTasks.value = repository.getPlantsWithTasksForDate(date)
    }
}
