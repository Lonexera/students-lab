package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.StubPlantWithTasksDao
import com.example.plantsapp.data.entity.RoomPlantWithTasks
import com.example.plantsapp.data.utils.createPlant
import com.example.plantsapp.data.utils.createRoomPlant
import com.example.plantsapp.data.utils.createRoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

@ExperimentalCoroutinesApi
class RoomTasksRepositoryTest {

    private val creationDate = Calendar.getInstance().apply {
        set(2021, 11, 10)
    }.time
    private val plantsWithTasks = listOf(
        RoomPlantWithTasks(
            createRoomPlant(name = "Greg", creationDateMillis = creationDate.time),
            listOf(
                createRoomTask(
                    plantName = "Greg",
                    frequency = 11
                ),
                createRoomTask(
                    plantName = "Greg",
                    frequency = 2
                )
            )
        ),
        RoomPlantWithTasks(
            createRoomPlant(name = "Roger", creationDateMillis = creationDate.time),
            listOf(
                createRoomTask(
                    plantName = "Roger",
                    frequency = 6
                ),
                createRoomTask(
                    plantName = "Roger",
                    frequency = 12
                )
            )
        )
    )

    @Test
    fun `tasks for creation date - returns all tasks`() = runBlockingTest {
        val dao = StubPlantWithTasksDao(plantsWithTasks)

        val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(creationDate)

        val expectedResult = listOf(
            createPlant(
                name = "Greg"
            ) to listOf(
                Task.WateringTask(11),
                Task.WateringTask(2)
            ),
            createPlant(
                name = "Roger"
            ) to listOf(
                Task.WateringTask(6),
                Task.WateringTask(12)
            )
        )
        result.collect {
            assertEquals(expectedResult, it)
        }
    }

    @Test
    fun `no fitting tasks for date - returns empty list`() = runBlockingTest {
        val date = Calendar.getInstance().apply {
            set(2021, 11, 11)
        }.time
        val dao = StubPlantWithTasksDao(plantsWithTasks)

        val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(date)

        val expectedResult = emptyList<Pair<Plant, List<Task>>>()
        result.collect {
            assertEquals(expectedResult, it)
        }
    }

    @Test
    fun `tasks for date after creation date - returns list of fitting tasks`() = runBlockingTest {
        val date = Calendar.getInstance().apply {
            set(2021, 11, 22)
        }.time
        val dao = StubPlantWithTasksDao(plantsWithTasks)

        val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(date)

        val expectedResult = listOf(
            createPlant(
                name = "Greg"
            ) to listOf(
                Task.WateringTask(2)
            ),
            createPlant(
                name = "Roger"
            ) to listOf(
                Task.WateringTask(6),
                Task.WateringTask(12)
            )
        )
        result.collect {
            assertEquals(expectedResult, it)
        }
    }

    @Test
    fun `tasks for date before creation date - returns empty list`() = runBlockingTest {
        val date = Calendar.getInstance().apply {
            set(2021, 11, 8)
        }.time
        val dao = StubPlantWithTasksDao(plantsWithTasks)

        val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(date)

        val expectedResult = emptyList<Pair<Plant, List<Task>>>()
        result.collect {
            assertEquals(expectedResult, it)
        }
    }

    @Test
    fun `empty plants and tasks lists - returns empty list`() = runBlockingTest {
        val dao = StubPlantWithTasksDao(emptyList())

        val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(creationDate)

        val expectedResult = emptyList<Pair<Plant, List<Task>>>()
        result.collect {
            assertEquals(expectedResult, it)
        }
    }

    @Test
    fun `empty tasks lists - returns empty list`() = runBlockingTest {
        val plantsWithTasksList = listOf(
            RoomPlantWithTasks(
                createRoomPlant(name = "Greg", creationDateMillis = creationDate.time),
                emptyList()
            ),
            RoomPlantWithTasks(
                createRoomPlant(name = "Roger", creationDateMillis = creationDate.time),
                emptyList()
            )
        )
        val dao = StubPlantWithTasksDao(plantsWithTasksList)

        val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(creationDate)

        val expectedResult = emptyList<Pair<Plant, List<Task>>>()
        result.collect {
            assertEquals(expectedResult, it)
        }
    }
}
