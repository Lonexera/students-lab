package com.example.plantsapp.data

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository

object PlantsRepositoryImpl : PlantsRepository {

    private const val SEVEN_DAYS: Int = 7
    private const val PLANT_PICTURE = "https://www.vippng.com/png/detail/41-414674_house-plant-png.png"

    private val listOfPlants: MutableList<Plant> = mutableListOf(
        Plant(Plant.PlantName("Bob"), "Succulent", PLANT_PICTURE, SEVEN_DAYS),
        Plant(Plant.PlantName("Marley"), "Spath plant", PLANT_PICTURE, SEVEN_DAYS),
        Plant(Plant.PlantName("John"), "Cacti", PLANT_PICTURE, SEVEN_DAYS),
        Plant(Plant.PlantName("Casey"), "Tillandsia", PLANT_PICTURE, SEVEN_DAYS),
        Plant(Plant.PlantName("Robert"), "Succulent", PLANT_PICTURE, SEVEN_DAYS)
    )

    override suspend fun fetchPlants(): List<Plant> {
        return listOfPlants
    }

    override suspend fun addPlant(plant: Plant) {
        listOfPlants.add(plant)
    }

    override suspend fun getPlantByName(name: Plant.PlantName): Plant {
        return listOfPlants.find { it.name == name }
            ?: error("Plant with name $name was not found")
    }
}
