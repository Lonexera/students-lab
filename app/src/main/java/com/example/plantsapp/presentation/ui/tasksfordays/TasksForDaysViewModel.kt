package com.example.plantsapp.presentation.ui.tasksfordays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksForDaysViewModel @Inject constructor() : ViewModel() {

    val todayDate: LiveData<Date> = MutableLiveData(Date())
}
