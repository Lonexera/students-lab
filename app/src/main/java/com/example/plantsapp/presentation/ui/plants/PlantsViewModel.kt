package com.example.plantsapp.presentation.ui.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.presentation.core.Event
import com.example.plantsapp.presentation.ui.utils.combineWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
@HiltViewModel
class PlantsViewModel @Inject constructor(
    @FirebaseQualifier private val repository: PlantsRepository
) : ViewModel() {

    private val allPlants: MutableLiveData<List<Plant>> = MutableLiveData()
    private val filter: MutableLiveData<String> = MutableLiveData("")
    val filteredPlants: LiveData<List<Plant>> = allPlants.combineWith(filter) { plants, filter ->
        plants?.filter {
            it.name.value.contains(filter!!.trim(), ignoreCase = true) ||
                    it.speciesName.contains(filter.trim(), ignoreCase = true)
        }
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _clickedPlant: MutableLiveData<Event<Plant>> = MutableLiveData()
    val clickedPlant: LiveData<Event<Plant>> get() = _clickedPlant
    private val _toCreation: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toCreation: LiveData<Event<Unit>> get() = _toCreation

    init {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.observePlants().collect {
                    allPlants.value = it

                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _isLoading.value = false
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
}
