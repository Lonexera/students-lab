package com.example.plantsapp.presentation.ui.utils

import java.util.Date

fun Date.plusDays(numberOfDays: Int): Date {
    return Date(this.time + (numberOfDays * DAY_IN_MILLISECONDS))
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24L
