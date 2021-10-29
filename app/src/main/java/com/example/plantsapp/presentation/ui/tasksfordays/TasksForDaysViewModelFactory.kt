package com.example.plantsapp.presentation.ui.tasksfordays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TasksForDaysViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksForDaysViewModel::class.java)) {
            return TasksForDaysViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
