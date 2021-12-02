package com.example.plantsapp.data.firebase.entity

import androidx.core.net.toUri
import com.example.plantsapp.domain.model.Plant

data class FirebasePlant(
    val plantName: String = "",
    val speciesName: String = "",
    val plantPicture: String = ""
) {

    fun toPlant(): Plant {
        return Plant(
            name = Plant.Name(plantName),
            speciesName = speciesName,
            plantPicture = plantPicture.toUri()
        )
    }

    companion object {
        fun from(plant: Plant): FirebasePlant {
            return FirebasePlant(
                plantName = plant.name.value,
                speciesName = plant.speciesName,
                plantPicture = plant.plantPicture.toString()
            )
        }
    }
}
