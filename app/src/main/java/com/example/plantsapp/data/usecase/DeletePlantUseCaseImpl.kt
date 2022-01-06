package com.example.plantsapp.data.usecase

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.usecase.DeletePlantUseCase
import javax.inject.Inject

class DeletePlantUseCaseImpl @Inject constructor(

) : DeletePlantUseCase {

    override suspend fun invoke(plant: Plant) {
        TODO("Not yet implemented")
    }
}
