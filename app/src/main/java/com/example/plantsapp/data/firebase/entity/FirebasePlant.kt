package com.example.plantsapp.data.firebase.entity

import android.net.Uri
import com.example.plantsapp.domain.model.Plant

data class FirebasePlant(
    val plantName: String = "",
    val speciesName: String = "",
    val plantPicture: String = ""
) {

    fun toPlant(localImageUri: Uri?): Plant {
        return Plant(
            name = Plant.Name(plantName),
            speciesName = speciesName,
            plantPicture = localImageUri
        )
    }

    companion object {
        fun from(plant: Plant, pictureUri: Uri?): FirebasePlant {
            return FirebasePlant(
                plantName = plant.name.value,
                speciesName = plant.speciesName,
                plantPicture = pictureUri?.toString() ?: ""
            )
        }
    }
}
