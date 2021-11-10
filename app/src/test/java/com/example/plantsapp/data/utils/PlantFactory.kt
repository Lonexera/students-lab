package com.example.plantsapp.data.utils

import androidx.core.net.toUri
import com.example.plantsapp.data.entity.RoomPlant
import com.example.plantsapp.domain.model.Plant

internal fun createRoomPlant(
    name: String = "",
    speciesName: String = "",
    plantPicture: String? = null,
    creationDateMillis: Long = 0
): RoomPlant {
    return RoomPlant(
        name = name,
        speciesName = speciesName,
        plantPicture = plantPicture,
        creationDateMillis = creationDateMillis
    )
}

internal fun createPlant(
    name: String = "",
    speciesName: String = "",
    plantPicture: String? = null
): Plant {
    return Plant(
        name = Plant.Name(name),
        speciesName = speciesName,
        plantPicture = plantPicture?.toUri()
    )
}
