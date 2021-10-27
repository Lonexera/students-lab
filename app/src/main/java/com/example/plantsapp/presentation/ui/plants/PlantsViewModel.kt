package com.example.plantsapp.presentation.ui.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.core.Event
import com.example.plantsapp.presentation.ui.plantcreation.combineWith
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlantsViewModel(
    private val repository: PlantsRepository
) : ViewModel() {

    private val allPlants: MutableLiveData<List<Plant>> = MutableLiveData(emptyList())
    private val filter: MutableLiveData<String> = MutableLiveData("")
    val filteredPlants: LiveData<List<Plant>>

    private val _clickedPlant: MutableLiveData<Event<Plant>> = MutableLiveData()
    val clickedPlant: LiveData<Event<Plant>> get() = _clickedPlant
    private val _toCreation: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toCreation: LiveData<Event<Unit>> get() = _toCreation

    init {
        filteredPlants = allPlants.combineWith(filter) { plants, filter ->
            filter(plants!!, filter!!)
        }

        viewModelScope.launch {
            repository.fetchPlants().collect {
                allPlants.value = it
            }
        }
    }

    fun onPlantClicked(plant: Plant) {
        _clickedPlant.value = Event(plant)
    }

    fun onAddPlantClicked() {
        _toCreation.value = Event(Unit)
    }

    fun onFilterChanged(query: String) {
        filter.value = query
    }

    private fun filter(allPlants: List<Plant>, filter: String): List<Plant> {
        return when {
            filter.isBlank() -> allPlants
            else -> {
                allPlants.filter {
                    it.name.value.contains(filter, ignoreCase = true) ||
                            it.speciesName.contains(filter, ignoreCase = true)
                }
            }
        }
    }
}
