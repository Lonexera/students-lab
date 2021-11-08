package com.example.plantsapp.presentation.ui.plantcreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksRepository

class CreationViewModelFactory(
    private val plantsRepository: PlantsRepository,
    private val tasksRepository: TasksRepository,
    private val validator: PlantCreationValidator
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantCreationViewModel::class.java)) {
            return PlantCreationViewModel(plantsRepository, tasksRepository, validator) as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
