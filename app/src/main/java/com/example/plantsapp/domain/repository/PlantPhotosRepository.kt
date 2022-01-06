package com.example.plantsapp.domain.repository

import android.net.Uri
import com.example.plantsapp.domain.model.Plant
import java.util.Date

interface PlantPhotosRepository {
    suspend fun savePhoto(plant: Plant, photoUri: Uri)
    suspend fun getPlantPhotos(plant: Plant): List<Pair<Uri, Date>>
    suspend fun deletePlantPhotos(plant: Plant)
}
