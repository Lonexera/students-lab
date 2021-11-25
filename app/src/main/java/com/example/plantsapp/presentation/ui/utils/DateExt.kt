package com.example.plantsapp.presentation.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

fun Date.plusDays(numberOfDays: Int): Date {
    return Date(this.time + (numberOfDays * DAY_IN_MILLISECONDS))
}

fun Date.formatDate(pattern: String): String {
    val simpleFormatter = SimpleDateFormat(pattern)

    return simpleFormatter.format(this)
}

fun Date.atStartDay(): Date {
    return Calendar.getInstance().apply {
        time = this@atStartDay
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun Date.atEndDay(): Date {
    return Calendar.getInstance().apply {
        time = this@atEndDay
        set(Calendar.HOUR_OF_DAY, END_DAY_HOUR)
        set(Calendar.MINUTE, END_DAY_MINUTE)
        set(Calendar.SECOND, END_DAY_SECOND)
        set(Calendar.MILLISECOND, END_DAY_MILLISECOND)
    }.time
}

fun Date.isSameDay(other: Date): Boolean {
    return this.atStartDay() == other.atStartDay()
}

/**
 * Counts days between start date and current date
 *  and  checks if the current date is a due date.
 */
fun Date.isDueDate(
    startDate: Date,
    intervalDays: Int
): Boolean {
    return (startDate.daysTo(this)) % intervalDays == 0
}

private fun Date.daysTo(nextDate: Date): Int {
    return ((nextDate.atStartDay().time - this.atStartDay().time) / DAY_IN_MILLISECONDS).toInt()
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24L
private const val END_DAY_HOUR = 23
private const val END_DAY_MINUTE = 59
private const val END_DAY_SECOND = 59
private const val END_DAY_MILLISECOND = 999
