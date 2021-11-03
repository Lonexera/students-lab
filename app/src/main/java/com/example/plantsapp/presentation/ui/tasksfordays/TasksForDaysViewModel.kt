package com.example.plantsapp.presentation.ui.tasksfordays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class TasksForDaysViewModel : ViewModel() {

    val todayDate: LiveData<Date> = MutableLiveData(Date())
}
