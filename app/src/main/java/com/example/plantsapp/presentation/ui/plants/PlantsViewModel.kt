package com.example.plantsapp.presentation.ui.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.presentation.core.Event

class PlantsViewModel : ViewModel() {

    val plants: LiveData<List<Plant>> = MutableLiveData(listOfPlants)
    private val _clickedPlant: MutableLiveData<Event<Plant>> = MutableLiveData()
    val clickedPlant: LiveData<Event<Plant>> get() = _clickedPlant
    private val _toCreation: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toCreation: LiveData<Event<Unit>> get() = _toCreation

    fun onPlantClicked(plant: Plant) {
        _clickedPlant.value = Event(plant)
    }

    fun onAddPlantClicked() {
        _toCreation.value = Event(Unit)
    }
}

private const val SEVEN_DAYS: Int = 7
private const val PLANT_PICTURE = "https://www.vippng.com/png/detail/41-414674_house-plant-png.png"

private val listOfPlants = listOf(
    Plant("Bob", "Succulent", PLANT_PICTURE, SEVEN_DAYS),
    Plant("Marley", "Spath plant", PLANT_PICTURE, SEVEN_DAYS),
    Plant("John", "Cacti", PLANT_PICTURE, SEVEN_DAYS),
    Plant("Casey", "Tillandsia", PLANT_PICTURE, SEVEN_DAYS),
    Plant("Robert", "Succulent", PLANT_PICTURE, SEVEN_DAYS)
)
