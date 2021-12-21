package com.example.statisticsapp.data.usecase

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import com.example.plantstatscontract.PlantStatisticsContract
import com.example.statisticsapp.domain.model.Plant
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
            ?.let { cursor ->
                val plants = cursor.getPlants()
                cursor.close()
                plants
            }
            ?: emptyList()
    }

    private fun Cursor.getPlants(): List<Plant> {
        val plants = mutableListOf<Plant>()

        val plantNameIndex = getColumnIndex(PlantStatisticsContract.FIELD_PLANT_NAME)
        val plantSpeciesNameIndex = getColumnIndex(PlantStatisticsContract.FIELD_SPECIES_NAME)
        val plantPictureIndex = getColumnIndex(PlantStatisticsContract.FIELD_PLANT_PICTURE)

        if (plantNameIndex >= 0 &&
            plantSpeciesNameIndex >= 0 &&
            plantPictureIndex >= 0
        ) {
            while (moveToNext()) {
                plants += Plant(
                    name = Plant.Name(
                        getString(plantNameIndex)
                    ),
                    speciesName = getString(plantSpeciesNameIndex),
                    plantPicture = getString(plantPictureIndex)?.toUri()
                )
            }
        }

        return plants
    }
}
