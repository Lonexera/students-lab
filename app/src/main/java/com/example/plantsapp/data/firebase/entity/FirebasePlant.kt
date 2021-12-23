package com.example.plantsapp.data.firebase.entity

import android.net.Uri
import androidx.core.net.toUri
import com.example.plantsapp.domain.model.Plant

data class FirebasePlant(
    val plantName: String = "",
    val speciesName: String = "",
    val plantPicture: String? = null
) {

    fun toPlant(): Plant {
        return Plant(
            name = Plant.Name(plantName),
            speciesName = speciesName,
            plantPicture = plantPicture
        )
    }

    companion object {
        fun from(plant: Plant, pictureUri: Uri?): FirebasePlant {
            return FirebasePlant(
                plantName = plant.name.value,
                speciesName = plant.speciesName,
                plantPicture = pictureUri?.toString()
            )
        }
    }
}
