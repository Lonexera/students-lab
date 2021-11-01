package com.example.plantsapp.presentation.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.repository.TasksRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date

class TasksViewModel(
    private val repository: TasksRepository
) : ViewModel() {

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        viewModelScope.launch {
             repository.getTasksForDate(Date()).collect {
                 _tasks.value = it
            }
        }
    }
}
