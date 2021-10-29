package com.example.plantsapp.presentation.ui.tasks

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.domain.model.Task

class TasksViewModel : ViewModel() {

    val tasks: LiveData<List<Task>> = MutableLiveData(
        listOf(
            Task(WATERING_ACTION, "Hector", PLANT_PICTURE.toUri()),
            Task(WATERING_ACTION, "Elvis", PLANT_PICTURE.toUri()),
            Task(WATERING_ACTION, "Arthur", PLANT_PICTURE.toUri()),
            Task(WATERING_ACTION, "Silvia", PLANT_PICTURE.toUri()),
            Task(WATERING_ACTION, "Robert", PLANT_PICTURE.toUri()),
        )
    )
}

private const val PLANT_PICTURE = "https://www.vippng.com/png/detail/41-414674_house-plant-png.png"
private const val WATERING_ACTION = "Water"
