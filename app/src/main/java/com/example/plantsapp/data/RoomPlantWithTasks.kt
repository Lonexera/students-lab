package com.example.plantsapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class RoomPlantWithTasks(
    @Embedded val plant: RoomPlant,
    @Relation(
        parentColumn = "name",
        entityColumn = "plantName"
    )
    val tasks: List<RoomTask>
)
