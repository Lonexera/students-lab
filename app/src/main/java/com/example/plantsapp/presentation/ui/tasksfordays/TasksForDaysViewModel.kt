package com.example.plantsapp.presentation.ui.tasksfordays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksForDaysViewModel : ViewModel() {

    val numberOfDays: LiveData<Int> = MutableLiveData(THIRTY_DAYS)
}

private const val THIRTY_DAYS = 30
