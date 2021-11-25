package com.example.plantsapp.data.usecase

import com.example.plantsapp.data.repository.StubTasksHistoryRepository
import com.example.plantsapp.data.repository.StubTasksRepository
import com.example.plantsapp.data.utils.createPlant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskWithState
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date
import java.util.Calendar

class GetTasksForPlantAndDateUseCaseImplTest {

    private fun createDate(year: Int, month: Int, date: Int): Date {
        return Calendar.getInstance().apply {
            set(year, month, date)
        }.time
    }

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

    private fun createUseCaseForGettingTasks(initialTasksList: List<Task>): GetTasksForPlantAndDateUseCaseImpl {
        return GetTasksForPlantAndDateUseCaseImpl(
            tasksRepository = StubTasksRepository(initialTasksList),
            tasksHistoryRepository = StubTasksHistoryRepository(false)
        )
    }

    @Test
    fun `tasks for creation date - returns all tasks`() = runBlockingTest {
        val creationDate = createDate(2021, 11, 10)
        val initialList = listOf(
            Task.WateringTask(frequency = 11),
            Task.SprayingTask(frequency = 2)
        )
        val useCase = createUseCaseForGettingTasks(initialList)

        val actual = useCase(
                plant = createPlant(creationDate = creationDate),
                currentDate = creationDate
            )
            .map { it.task }

        val expectedResult = listOf(
            Task.WateringTask(11),
            Task.SprayingTask(2)
        )
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `no fitting tasks for date - returns empty list`() = runBlockingTest {
        val todayDate = createDate(2021, 11, 11)
        val creationDate = createDate(2021, 11, 10)
        val initialList = listOf(
            Task.WateringTask(frequency = 11),
            Task.SprayingTask(frequency = 2)
        )
        val useCase = createUseCaseForGettingTasks(initialList)

        val actual = useCase(
                plant = createPlant(creationDate = creationDate),
                currentDate = todayDate
            )

        val expectedResult = emptyList<TaskWithState>()
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `tasks for date after creation date - returns list of fitting tasks`() = runBlockingTest {
        val date = createDate(2021, 11, 22)
        val creationDate = createDate(2021, 11, 10)
        val initialList = listOf(
            Task.WateringTask(frequency = 11),
            Task.SprayingTask(frequency = 2)
        )
        val useCase = createUseCaseForGettingTasks(initialList)

        val actual = useCase(
                plant = createPlant(creationDate = creationDate),
                currentDate = date
            )
            .map { it.task }

        val expectedResult = listOf(
            Task.SprayingTask(2),
        )
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `when plant with no tasks - return empty list`() = runBlockingTest {
        val date = createDate(2021, 11, 10)
        val useCase = createUseCaseForGettingTasks(emptyList())

        val actual = useCase(
                plant = createPlant(creationDate = date),
                currentDate = date
            )

        val expectedResult = emptyList<TaskWithState>()
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `tasks for few hours after creation date(but new day is up)- returns empty list`() =
        runBlockingTest {
            val date = createAccurateDate(2021, 11, 11, 4, 13)
            val creationDate = createAccurateDate(2021, 11, 10, 18, 39)
            val initialList = listOf(
                Task.WateringTask(frequency = 11),
                Task.SprayingTask(frequency = 2)
            )
            val useCase = createUseCaseForGettingTasks(initialList)

            val actual = useCase(
                    plant = createPlant(creationDate = creationDate),
                    currentDate = date
                )

            val expectedResult = emptyList<TaskWithState>()
            assertEquals(expectedResult, actual)
        }
}
