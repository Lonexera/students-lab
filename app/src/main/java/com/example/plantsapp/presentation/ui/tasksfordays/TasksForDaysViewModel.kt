package com.example.plantsapp.presentation.ui.tasksfordays

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.presentation.ui.utils.formatDateToStandard
import com.example.plantsapp.presentation.ui.utils.formatDateWithoutYear
import com.example.plantsapp.presentation.ui.utils.minusDays
import com.example.plantsapp.presentation.ui.utils.plusDays
import java.util.Date

class TasksForDaysViewModel : ViewModel() {

    val todayDate: LiveData<Date> = MutableLiveData(Date())

    private val _currentPageDate: MutableLiveData<String> = MutableLiveData()
    val currentPageDate: LiveData<String> get() = _currentPageDate

    private val _prevPageDate: MutableLiveData<String> = MutableLiveData()
    val prevPageDate: LiveData<String> get() = _prevPageDate

    private val _nextPageDate: MutableLiveData<String> = MutableLiveData()
    val nextPageDate: LiveData<String> get() = _nextPageDate

    private val _prevButtonVisibility: MutableLiveData<Int> = MutableLiveData()
    val prevButtonVisibility: LiveData<Int> get() = _prevButtonVisibility

    fun onCurrentPageChange(offset: Int) {
        _prevButtonVisibility.value = View.VISIBLE
        val pageDate = todayDate.value!!.plusDays(offset)

        when (offset) {
            0 -> {
                _prevButtonVisibility.value = View.GONE
                _currentPageDate.value = "Today"
                _nextPageDate.value = "Tomorrow"
            }
            1 -> {
                _prevPageDate.value = "Today"
                _currentPageDate.value = "Tomorrow"
                _nextPageDate.value = pageDate.plusDays(1).formatDateWithoutYear()
            }
            2 -> {
                _prevPageDate.value = "Tomorrow"
                _currentPageDate.value = pageDate.formatDateToStandard()
                _nextPageDate.value = pageDate.plusDays(1).formatDateWithoutYear()
            }
            else -> {
                _prevPageDate.value = pageDate.minusDays(1).formatDateWithoutYear()
                _currentPageDate.value = pageDate.formatDateToStandard()
                _nextPageDate.value = pageDate.plusDays(1).formatDateWithoutYear()
            }
        }
    }
}
