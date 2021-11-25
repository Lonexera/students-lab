package com.example.plantsapp.presentation.ui.utils

import org.junit.Test
import java.util.Date
import java.util.Calendar
import org.junit.Assert.assertEquals

class DateExtDueDateTest {

    private fun createAccurateDate(
        year: Int,
        month: Int,
        date: Int,
        hour: Int,
        minute: Int
    ): Date {
        return Calendar.getInstance().apply {
            set(year, month, date)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.time
    }

    @Test
    fun `current date is start date but with different time - returns true`() {
        val currentDate = createAccurateDate(2021, 10, 17, 18, 43)
        val startDate = createAccurateDate(2021, 10, 17, 1, 53)
        val interval = 3

        val actual = currentDate.isDueDate(
            startDate = startDate,
            intervalDays = interval
        )

        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun `current date is a due date - returns true`() {
        val currentDate = createAccurateDate(2021, 10, 20, 18, 43)
        val startDate = createAccurateDate(2021, 10, 17, 1, 53)
        val interval = 3

        val actual = currentDate.isDueDate(
            startDate = startDate,
            intervalDays = interval
        )

        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun `current date is one day ahead of due date - returns false`() {
        val currentDate = createAccurateDate(2021, 10, 21, 18, 43)
        val startDate = createAccurateDate(2021, 10, 17, 1, 53)
        val interval = 3

        val actual = currentDate.isDueDate(
            startDate = startDate,
            intervalDays = interval
        )

        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `current date is one minute away of due date - returns false`() {
        val currentDate = createAccurateDate(2021, 10, 19, 23, 59)
        val startDate = createAccurateDate(2021, 10, 17, 0, 0)
        val interval = 3

        val actual = currentDate.isDueDate(
            startDate = startDate,
            intervalDays = interval
        )

        val expected = false
        assertEquals(expected, actual)
    }
}
