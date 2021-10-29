package com.example.plantsapp.presentation.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TasksViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
