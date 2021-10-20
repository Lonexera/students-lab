package com.example.plantsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsapp.domain.model.Plant

@Entity(tableName = "plants")
data class RoomPlant(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "species_name") val speciesName: String,
    @ColumnInfo(name = "plant_picture") val plantPicture: String,
    @ColumnInfo(name = "watering_frequency") val wateringFrequencyDays: Int
) {
    companion object {
        fun from(plant: Plant): RoomPlant {
            return RoomPlant(
                plant.name.value,
                plant.speciesName,
                plant.plantPicture,
                plant.wateringFrequencyDays
            )
        }
    }

    fun toPlant(): Plant {
        return Plant(
            Plant.Name(this.name),
            this.speciesName,
            this.plantPicture,
            this.wateringFrequencyDays
        )
    }
}
