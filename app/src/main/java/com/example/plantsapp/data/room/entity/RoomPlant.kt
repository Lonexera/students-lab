package com.example.plantsapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Plant

@Deprecated("Room will no longer be used in app")
@Entity(tableName = "plants")
data class RoomPlant(
    @PrimaryKey val name: String,
    val speciesName: String,
    val plantPicture: String?
) {
    companion object {
        fun from(plant: Plant): RoomPlant {
            return RoomPlant(
                plant.name.value,
                plant.speciesName,
                plant.plantPicture?.toString(),
            )
        }
    }

    fun toPlant(): Plant {
        return Plant(
            Plant.Name(name),
            speciesName,
            plantPicture,
        )
    }
}
