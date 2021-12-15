package com.example.plantsapp.domain.repository

import android.net.Uri
import com.example.plantsapp.domain.model.Plant

interface PlantPhotosRepository {
    suspend fun savePhoto(plant: Plant, photoUri: Uri)
}
