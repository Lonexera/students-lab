package com.example.plantsapp.presentation.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class TasksViewModel(
    private val repository: TasksRepository,
    private val date: Date
) : ViewModel() {

    private val _plantsWithTasks: MutableLiveData<List<Pair<Plant, List<Task>>>> = MutableLiveData()
    val plantsWithTasks: LiveData<List<Pair<Plant, List<Task>>>> = _plantsWithTasks

    init {
        viewModelScope.launch {
             repository.getPlantsWithTasksForDate(date).collect {
                 _plantsWithTasks.value = it
            }
        }
    }
}
