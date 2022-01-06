package com.example.plantsapp.data.usecase

import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantPhotosRepository
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.domain.repository.TasksRepository
import com.example.plantsapp.domain.usecase.DeletePlantUseCase
import javax.inject.Inject

class DeletePlantUseCaseImpl @Inject constructor(
    @FirebaseQualifier private val plantsRepository: PlantsRepository,
    @FirebaseQualifier private val tasksRepository: TasksRepository,
    @FirebaseQualifier private val tasksHistoryRepository: TasksHistoryRepository,
    @FirebaseQualifier private val plantPhotosRepository: PlantPhotosRepository
) : DeletePlantUseCase {

    override suspend fun invoke(plant: Plant) {
        tasksRepository.getTasksForPlant(plant).forEach { task ->
            tasksHistoryRepository.deleteTaskHistory(plant, task)
        }
        tasksRepository.deleteAllTasks(plant)
        plantPhotosRepository.deletePlantPhotos(plant)
        plantsRepository.deletePlant(plant)
    }
}
