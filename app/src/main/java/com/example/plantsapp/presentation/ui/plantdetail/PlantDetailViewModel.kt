package com.example.plantsapp.presentation.ui.plantdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.domain.model.Plant

class PlantDetailViewModel : ViewModel() {

    val plant: LiveData<Plant> = MutableLiveData(detailPlant)
}

private const val SEVEN_DAYS: Int = 7
private const val PLANT_PICTURE = "https://www.vippng.com/png/detail/41-414674_house-plant-png.png"

private val detailPlant = Plant("Bob", "Succulent", PLANT_PICTURE, SEVEN_DAYS)
