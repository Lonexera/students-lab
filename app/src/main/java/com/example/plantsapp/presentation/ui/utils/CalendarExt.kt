package com.example.plantsapp.presentation.ui.utils

import java.util.Calendar
import java.util.concurrent.TimeUnit

fun Calendar.calculateDelay(
    nextDayHour: Int = 0,
    nextDayMinute: Int = 0,
    nextDaySecond: Int = 0
): Long {

    val firstScheduledDate = (this.clone() as Calendar).apply {
        set(Calendar.HOUR_OF_DAY, nextDayHour)
        set(Calendar.MINUTE, nextDayMinute)
        set(Calendar.SECOND, nextDaySecond)
    }

    return firstScheduledDate.timeInMillis + TimeUnit.DAYS.toMillis(1) - this.timeInMillis
}

fun Calendar.getNextDayInEpoch(
    nextDayHour: Int = 0,
    nextDayMinute: Int = 0,
    nextDaySecond: Int = 0
): Long = (this.clone() as Calendar)
    .apply {
        set(Calendar.HOUR_OF_DAY, nextDayHour)
        set(Calendar.MINUTE, nextDayMinute)
        set(Calendar.SECOND, nextDaySecond)
        add(Calendar.DATE, 1)
    }
    .timeInMillis
