package com.example.plantsapp.data.repository

import com.example.plantsapp.data.dao.StubPlantWithTasksDao
import com.example.plantsapp.data.entity.TaskKeys
import com.example.plantsapp.data.utils.createPlant
import com.example.plantsapp.data.utils.createRoomPlant
import com.example.plantsapp.data.utils.createRoomTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

@ExperimentalCoroutinesApi
class RoomTasksRepositoryTest {

    private val creationDate = Calendar.getInstance().apply {
        set(2021, 11, 10)
    }.time
    private val defaultPlants = listOf(
        createRoomPlant(
            name = "Greg",
            creationDateMillis = creationDate.time
        ),
        createRoomPlant(
            name = "Roger",
            creationDateMillis = creationDate.time
        )
    )
    private val defaultTasks = listOf(
        createRoomTask(
            taskKey = TaskKeys.WATERING_TASK,
            plantName = "Greg",
            frequency = 11
        ),
        createRoomTask(
            taskKey = TaskKeys.SPRAYING_TASK,
            plantName = "Greg",
            frequency = 2
        ),
        createRoomTask(
            taskKey = TaskKeys.SPRAYING_TASK,
            plantName = "Roger",
            frequency = 6
        ),
        createRoomTask(
            taskKey = TaskKeys.LOOSENING_TASK,
            plantName = "Roger",
            frequency = 12
        )
    )

    @Test
    fun `tasks for creation date - returns all tasks`() {
        TestCoroutineScope().launch {
            val dao = StubPlantWithTasksDao(defaultPlants, defaultTasks)

            val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(creationDate)

            val expectedResult = listOf(
                createPlant(
                    name = "Greg"
                ) to listOf(
                    Task.WateringTask(11),
                    Task.SprayingTask(2)
                ),
                createPlant(
                    name = "Roger"
                ) to listOf(
                    Task.SprayingTask(5),
                    Task.LooseningTask(12)
                )
            )
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `empty plants and tasks lists - returns empty list`() {
        TestCoroutineScope().launch {
            val dao = StubPlantWithTasksDao(emptyList(), emptyList())

            val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(creationDate)

            val expectedResult = emptyList<Pair<Plant, List<Task>>>()
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `empty tasks lists - returns empty list`() {
        TestCoroutineScope().launch {
            val dao = StubPlantWithTasksDao(defaultPlants, emptyList())

            val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(creationDate)

            val expectedResult = emptyList<Pair<Plant, List<Task>>>()
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `no fitting tasks for date - returns empty list`() {
        TestCoroutineScope().launch {
            val date = Calendar.getInstance().apply {
                set(2021, 11, 11)
            }.time

            val dao = StubPlantWithTasksDao(defaultPlants, defaultTasks)

            val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(date)

            val expectedResult = emptyList<Pair<Plant, List<Task>>>()
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `tasks for date after creation date - returns list of fitting tasks`() {
        TestCoroutineScope().launch {
            val date = Calendar.getInstance().apply {
                set(2021, 11, 22)
            }.time
            val dao = StubPlantWithTasksDao(defaultPlants, defaultTasks)

            val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(date)

            val expectedResult = listOf(
                createPlant(
                    name = "Greg"
                ) to listOf(
                    Task.SprayingTask(2)
                ),
                createPlant(
                    name = "Roger"
                ) to listOf(
                    Task.LooseningTask(12)
                )
            )
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `tasks for date before creation date - returns list of fitting tasks`() {
        TestCoroutineScope().launch {
            val date = Calendar.getInstance().apply {
                set(2021, 11, 4)
            }.time

            val dao = StubPlantWithTasksDao(defaultPlants, defaultTasks)

            val result = RoomTasksRepository(dao).getPlantsWithTasksForDate(date)

            val expectedResult = listOf(
                createPlant(
                    name = "Greg"
                ) to listOf(
                    Task.SprayingTask(2)
                ),
                createPlant(
                    name = "Roger"
                ) to listOf(
                    Task.SprayingTask(6)
                )
            )
            assertEquals(expectedResult, result)
        }
    }
}
