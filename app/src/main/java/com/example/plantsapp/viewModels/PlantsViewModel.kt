package com.example.plantsapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.model.Plant
import com.example.plantsapp.model.listOfPlants

class PlantsViewModel : ViewModel() {

    val plants: LiveData<List<Plant>> by lazy {
        MutableLiveData(listOfPlants)
    }
}
