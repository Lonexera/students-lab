package com.example.plantsapp.data.contentprovider.usecase

import android.database.MatrixCursor
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantstatscontract.PlantStatisticsContract
import javax.inject.Inject

class CreatePlantsCursorUseCase @Inject constructor(
    @FirebaseQualifier private val plantsRepository: PlantsRepository
) {
    suspend operator fun invoke(): MatrixCursor {
        return MatrixCursor(
            arrayOf(
                PlantStatisticsContract.Plants.FIELD_NAME,
                PlantStatisticsContract.Plants.FIELD_SPECIES_NAME,
                PlantStatisticsContract.Plants.FIELD_PICTURE
            )
        )
            .apply {
                plantsRepository
                    .fetchPlants()
                    .forEach { putPlant(it) }
            }
    }

    private fun MatrixCursor.putPlant(plant: Plant) {
        newRow()
            .add(PlantStatisticsContract.Plants.FIELD_NAME, plant.name.value)
            .add(PlantStatisticsContract.Plants.FIELD_SPECIES_NAME, plant.speciesName)
            .add(PlantStatisticsContract.Plants.FIELD_PICTURE, plant.plantPicture?.toString())
    }
}
