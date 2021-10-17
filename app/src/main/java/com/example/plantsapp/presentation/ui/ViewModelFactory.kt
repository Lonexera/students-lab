package com.example.plantsapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.ui.plantcreation.PlantCreationViewModel
import com.example.plantsapp.presentation.ui.plantdetail.PlantDetailViewModel
import com.example.plantsapp.presentation.ui.plants.PlantsViewModel

class ViewModelFactory(
    private val repository: PlantsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PlantsViewModel::class.java) -> {
                PlantsViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PlantDetailViewModel::class.java) -> {
                PlantDetailViewModel() as T
            }

            modelClass.isAssignableFrom(PlantCreationViewModel::class.java) -> {
                PlantCreationViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
