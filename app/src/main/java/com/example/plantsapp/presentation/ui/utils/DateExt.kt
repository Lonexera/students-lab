package com.example.plantsapp.presentation.ui.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.plusDays(numberOfDays: Int): Date {
    return Date(this.time + (numberOfDays * DAY_IN_MILLISECONDS))
}

fun Date.formatDate(pattern: String): String {
    val simpleFormatter = SimpleDateFormat(pattern)

    return simpleFormatter.format(this)
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24L
