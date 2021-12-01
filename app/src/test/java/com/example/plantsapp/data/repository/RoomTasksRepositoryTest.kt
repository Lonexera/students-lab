package com.example.plantsapp.data.repository

import org.junit.Assert.assertEquals
import com.example.plantsapp.data.dao.StubTasksDao
import com.example.plantsapp.data.entity.RoomTask
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.data.utils.createPlant
import com.example.plantsapp.data.utils.createRoomTask
import com.example.plantsapp.domain.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class RoomTasksRepositoryTest {

    private val date = Date()
    private fun createRepository(initialList: List<RoomTask>) =
        RoomTasksRepository(StubTasksDao(initialList))

    @Test
    fun `mapping from RoomTask to Task`() = runBlockingTest {
        val initialList = listOf(
            createRoomTask(
                taskKey = TaskKeys.WATERING_TASK,
                frequency = 11,
                lastUpdateDate = date
            ),
            createRoomTask(
                taskKey = TaskKeys.LOOSENING_TASK,
                frequency = 2,
                lastUpdateDate = date
            )
        )
        val repository = createRepository(initialList)

        val actual = repository.getTasksForPlant(createPlant())

        val expected = listOf(
            Task.WateringTask(frequency = 11, date),
            Task.LooseningTask(frequency = 2, date)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `for empty tasks list - returns empty list`() = runBlockingTest {
        val repository = createRepository(emptyList())

        val actual = repository.getTasksForPlant(createPlant())

        val expected = emptyList<Task>()
        assertEquals(expected, actual)
    }
}
