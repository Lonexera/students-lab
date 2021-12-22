package com.example.statisticsapp.data.usecase

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.plantsapp.domain.model.Plant
import com.example.plantstatscontract.PlantStatisticsContract
import com.example.statisticsapp.domain.usecase.GetPlantsUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetPlantsUseCaseImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : GetPlantsUseCase {

    override suspend fun invoke(): List<Plant> {
        return appContext.contentResolver.query(
            Uri.parse(PlantStatisticsContract.CONTENT_URI),
            null,
            null,
            null,
            null
        )
            ?.use { it.getPlants() } ?: emptyList()
    }

    private fun Cursor.getPlants(): List<Plant> {
        val plantNameIndex = getColumnIndex(PlantStatisticsContract.FIELD_PLANT_NAME)
        val plantSpeciesNameIndex = getColumnIndex(PlantStatisticsContract.FIELD_SPECIES_NAME)
        val plantPictureIndex = getColumnIndex(PlantStatisticsContract.FIELD_PLANT_PICTURE)

        return when (
            checkIfColumnNamesExist(plantNameIndex, plantSpeciesNameIndex, plantPictureIndex)
        ) {
            true -> {
                val plants = mutableListOf<Plant>()
                while (moveToNext()) {
                    plants += getPlant(plantNameIndex, plantSpeciesNameIndex, plantPictureIndex)
                }
                plants
            }
            false -> emptyList()
        }
    }

    private fun Cursor.getPlant(
        plantNameIndex: Int,
        plantSpeciesNameIndex: Int,
        plantPictureIndex: Int
    ): Plant {
        return Plant(
            name = Plant.Name(
                getString(plantNameIndex)
            ),
            speciesName = getString(plantSpeciesNameIndex),
            plantPicture = getString(plantPictureIndex)
        )
    }

    private fun checkIfColumnNamesExist(
        plantNameIndex: Int,
        plantSpeciesNameIndex: Int,
        plantPictureIndex: Int
    ): Boolean {
        return plantNameIndex >= 0 && plantSpeciesNameIndex >= 0 && plantPictureIndex >= 0
    }
}
