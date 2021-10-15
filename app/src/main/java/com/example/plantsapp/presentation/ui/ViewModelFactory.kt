package com.example.plantsapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsapp.data.Repository
import com.example.plantsapp.presentation.ui.plantcreation.PlantCreationViewModel
import com.example.plantsapp.presentation.ui.plantdetail.PlantDetailViewModel
import com.example.plantsapp.presentation.ui.plants.PlantsViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PlantsViewModel::class.java) -> {
                PlantsViewModel(Repository) as T
            }

            modelClass.isAssignableFrom(PlantDetailViewModel::class.java) -> {
                PlantDetailViewModel() as T
            }

            modelClass.isAssignableFrom(PlantCreationViewModel::class.java) -> {
                PlantCreationViewModel(Repository) as T
            }

            else -> throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
