package com.example.plantsapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoomPlantWithTasks(
    @Embedded val plant: RoomPlant,
    @Relation(
        parentColumn = "name",
        entityColumn = "plant_name"
    )
    val tasks: List<RoomTask>
)
