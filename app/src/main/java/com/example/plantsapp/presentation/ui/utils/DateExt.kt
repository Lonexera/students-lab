package com.example.plantsapp.presentation.ui.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.plusDays(numberOfDays: Int): Date {
    return Date(this.time + (numberOfDays * DAY_IN_MILLISECONDS))
}

fun Date.formatDateToStandard(): String {
    val simpleFormatter = SimpleDateFormat("d MMM yyyy")

    return simpleFormatter.format(this)
}

fun Date.formatDateWithoutYear(): String {
    val simpleFormatter = SimpleDateFormat("d MMM")

    return simpleFormatter.format(this)
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24L
