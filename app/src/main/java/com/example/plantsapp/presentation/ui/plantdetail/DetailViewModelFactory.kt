package com.example.plantsapp.presentation.ui.plantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsapp.domain.repository.PlantsRepository

class DetailViewModelFactory(
    private val repository: PlantsRepository,
    private val plantName: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailViewModel::class.java)) {
            return PlantDetailViewModel(repository, plantName) as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
