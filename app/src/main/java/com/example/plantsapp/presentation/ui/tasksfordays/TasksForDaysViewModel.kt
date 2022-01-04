package com.example.plantsapp.presentation.ui.tasksfordays

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.presentation.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksForDaysViewModel @Inject constructor(
    private val statisticsAppResolver: StatisticsAppResolver
) : ViewModel() {

    val todayDate: LiveData<Date> = MutableLiveData(Date())

    val isStatisticsAppInstalled: LiveData<Boolean> = MutableLiveData(
        statisticsAppResolver.isAppInstalled()
    )

    private val _launchStatisticsApp: MutableLiveData<Event<Intent>> = MutableLiveData()
    val launchStatisticsApp: LiveData<Event<Intent>>get() = _launchStatisticsApp

    fun onStatisticsClicked() {
        _launchStatisticsApp.value = Event(statisticsAppResolver.requireIntent())
    }
}
