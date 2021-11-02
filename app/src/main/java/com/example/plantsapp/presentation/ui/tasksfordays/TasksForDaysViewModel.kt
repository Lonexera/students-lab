package com.example.plantsapp.presentation.ui.tasksfordays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.presentation.ui.utils.plusDays
import java.util.Date

class TasksForDaysViewModel : ViewModel() {

    val todayDate: LiveData<Date> = MutableLiveData(Date())

    private val _currentPageDate: MutableLiveData<Date> = MutableLiveData()
    val currentPageDate: LiveData<Date> get() = _currentPageDate

    fun onCurrentPageChange(offset: Int) {
        _currentPageDate.value = todayDate.value!!.plusDays(offset)
    }
}
