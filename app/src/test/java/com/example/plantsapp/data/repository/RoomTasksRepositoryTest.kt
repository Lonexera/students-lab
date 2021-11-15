package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.StubPlantWithTasksDao
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.entity.TaskKeys
import com.example.plantsapp.data.utils.createRoomPlant
import com.example.plantsapp.data.utils.createRoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date
import java.util.Calendar

@ExperimentalCoroutinesApi
class RoomTasksRepositoryTest {

    private fun createDate(year: Int, month: Int, date: Int): Date {
        return Calendar.getInstance().apply {
            set(year, month, date)
        }.time
    }

    @Test
    fun `tasks for creation date - returns all tasks`() = runBlockingTest {
        val creationDate = createDate(2021, 11, 10)
        val initialList = listOf(
            RoomPlantWithTasks(
                createRoomPlant(creationDateMillis = creationDate.time),
                listOf(
                    createRoomTask(
                        taskKey = TaskKeys.WATERING_TASK,
                        frequency = 11
                    ),
                    createRoomTask(
                        taskKey = TaskKeys.SPRAYING_TASK,
                        frequency = 2
                    )
                )
            )
        )
        val dao = StubPlantWithTasksDao(initialList)

        val actual = RoomTasksRepository(dao)
            .getPlantsWithTasksForDate(creationDate)
            .first()
            .map { (_, tasks) -> tasks }
            .first()

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
            RoomPlantWithTasks(
                createRoomPlant(creationDateMillis = creationDate.time),
                listOf(
                    createRoomTask(
                        taskKey = TaskKeys.WATERING_TASK,
                        frequency = 11
                    ),
                    createRoomTask(
                        taskKey = TaskKeys.SPRAYING_TASK,
                        frequency = 2
                    )
                )
            )
        )
        val dao = StubPlantWithTasksDao(initialList)

        val actual = RoomTasksRepository(dao)
            .getPlantsWithTasksForDate(todayDate)
            .first()

        val expectedResult = emptyList<Pair<Plant, List<Task>>>()
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `tasks for date after creation date - returns list of fitting tasks`() = runBlockingTest {
        val date = createDate(2021, 11, 22)
        val creationDate = createDate(2021, 11, 10)
        val initialList = listOf(
            RoomPlantWithTasks(
                createRoomPlant(creationDateMillis = creationDate.time),
                listOf(
                    createRoomTask(
                        taskKey = TaskKeys.WATERING_TASK,
                        frequency = 11
                    ),
                    createRoomTask(
                        taskKey = TaskKeys.SPRAYING_TASK,
                        frequency = 2
                    )
                )
            )
        )
        val dao = StubPlantWithTasksDao(initialList)

        val actual = RoomTasksRepository(dao)
            .getPlantsWithTasksForDate(date)
            .first()
            .map { (_, tasks) -> tasks }
            .first()

        val expectedResult = listOf(
            Task.SprayingTask(2),
        )
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `when empty plants list - return empty list`() = runBlockingTest {
        val date = createDate(2021, 11, 10)
        val dao = StubPlantWithTasksDao(emptyList())

        val actual = RoomTasksRepository(dao)
            .getPlantsWithTasksForDate(date)
            .first()

        val expectedResult = emptyList<RoomPlantWithTasks>()
        assertEquals(expectedResult, actual)
    }

    @Test
    fun `when plant with no tasks - return empty list`() = runBlockingTest {
        val date = createDate(2021, 11, 10)
        val initialList = listOf(
            RoomPlantWithTasks(
                createRoomPlant(creationDateMillis = date.time),
                emptyList()
            )
        )
        val dao = StubPlantWithTasksDao(initialList)

        val actual = RoomTasksRepository(dao)
            .getPlantsWithTasksForDate(date)
            .first()

        val expectedResult = emptyList<Pair<Plant, List<Task>>>()
        assertEquals(expectedResult, actual)
    }
}
