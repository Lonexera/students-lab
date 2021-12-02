package com.example.plantsapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsapp.data.room.entity.RoomTaskCompletion

@Dao
interface RoomTaskHistoryDao {

    @Query("SELECT COUNT(taskId) FROM taskHistory " +
            "WHERE taskId = :taskId AND " +
            "completionDate BETWEEN :startDay AND :endDay")
    suspend fun isTaskCompletedForDate(taskId: Int, startDay: Long, endDay: Long): Boolean

    @Insert
    suspend fun addTaskCompletionDate(taskCompletion: RoomTaskCompletion)
}
