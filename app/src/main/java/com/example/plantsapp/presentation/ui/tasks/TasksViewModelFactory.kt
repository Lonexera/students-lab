package com.example.plantsapp.presentation.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsapp.domain.repository.PlantsRepository

class TasksViewModelFactory(
    private val repository: PlantsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
