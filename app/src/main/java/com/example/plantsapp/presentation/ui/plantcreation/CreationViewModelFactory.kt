package com.example.plantsapp.presentation.ui.plantcreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsapp.domain.repository.PlantsRepository

class CreationViewModelFactory(
    private val repository: PlantsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantCreationViewModel::class.java)) {
            return PlantCreationViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
