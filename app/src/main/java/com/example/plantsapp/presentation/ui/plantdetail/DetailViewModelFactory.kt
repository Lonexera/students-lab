package com.example.plantsapp.presentation.ui.plantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailViewModel::class.java)) {
            return PlantDetailViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Class Is Not Found!")
        }
    }
}
