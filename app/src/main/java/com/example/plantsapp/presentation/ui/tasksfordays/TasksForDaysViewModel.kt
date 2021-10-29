package com.example.plantsapp.presentation.ui.tasksfordays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksForDaysViewModel : ViewModel() {

    val numberOfDays: LiveData<Int> = MutableLiveData(THREE_DAYS)
    val tabTexts: LiveData<List<String>> = MutableLiveData(
        listOf("Today", "Tomorrow", "30 Oct")
    )
}

private const val THREE_DAYS = 3
