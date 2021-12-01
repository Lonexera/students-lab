package com.example.plantsapp.data.utils

import android.net.Uri
import com.example.plantsapp.domain.model.Plant

internal fun createPlant(
    name: String = "",
    speciesName: String = "",
    plantPicture: Uri? = null
): Plant {
    return Plant(
        name = Plant.Name(name),
        speciesName = speciesName,
        plantPicture = plantPicture,
    )
}
