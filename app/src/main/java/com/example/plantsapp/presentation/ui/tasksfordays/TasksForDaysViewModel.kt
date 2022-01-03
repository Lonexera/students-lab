package com.example.plantsapp.presentation.ui.tasksfordays

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksForDaysViewModel @Inject constructor(
    private val statisticsAppResolver: StatisticsAppResolver
) : ViewModel() {

    val todayDate: LiveData<Date> = MutableLiveData(Date())

    private val _isStatisticsAppInstalled: MutableLiveData<Boolean> = MutableLiveData(
        statisticsAppResolver.isAppInstalled()
    )
    val isStatisticsAppInstalled: LiveData<Boolean> get() = _isStatisticsAppInstalled

    private val _launchStatisticsApp: MutableLiveData<Intent> = MutableLiveData()
    val launchStatisticsApp: LiveData<Intent> get() = _launchStatisticsApp

    fun onStatisticsClicked() {
        _launchStatisticsApp.value = statisticsAppResolver.intent
    }
}
