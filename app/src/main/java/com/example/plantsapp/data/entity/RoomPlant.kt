package com.example.plantsapp.data.entity

import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Plant
import java.util.Date

@Entity(tableName = "plants")
data class RoomPlant(
    @PrimaryKey val name: String,
    val speciesName: String,
    val plantPicture: String?,
    val creationDateMillis: Long
) {
    companion object {
        fun from(plant: Plant, creationDate: Date): RoomPlant {
            return RoomPlant(
                plant.name.value,
                plant.speciesName,
                plant.plantPicture?.toString(),
                creationDate.time
            )
        }
    }

    fun toPlant(): Plant {
        return Plant(
            Plant.Name(name),
            speciesName,
            plantPicture?.toUri()
        )
    }
}
