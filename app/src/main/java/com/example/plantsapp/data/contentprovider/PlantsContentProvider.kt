package com.example.plantsapp.data.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantstatscontract.PlantStatisticsContract
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking

class PlantsContentProvider : ContentProvider() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface PlantsContentProviderEntryPoint {
        @FirebaseQualifier
        fun plantsRepository(): PlantsRepository
    }

    private lateinit var hiltEntryPoint: PlantsContentProviderEntryPoint
    private val plantsRepository: PlantsRepository by lazy {
        hiltEntryPoint.plantsRepository()
    }

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext
            ?: throw IllegalStateException("Unable to get applicationContext")

        hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            PlantsContentProviderEntryPoint::class.java
        )
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        return when (uri.toString()) {
            PlantStatisticsContract.CONTENT_URI -> createPlantsCursor()
            else -> throw IllegalStateException("Provided uri is not supported")
        }
    }

    override fun getType(uri: Uri): String {
        return PlantStatisticsContract.CONTENT_TYPE
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not implemented")
    }

    private fun createPlantsCursor(): MatrixCursor {
        return MatrixCursor(
            arrayOf(
                PlantStatisticsContract.FIELD_PLANT_NAME,
                PlantStatisticsContract.FIELD_SPECIES_NAME,
                PlantStatisticsContract.FIELD_PLANT_PICTURE
            )
        )
            .apply {
                runBlocking {
                    plantsRepository
                        .fetchPlants()
                        .forEach { putPlant(it) }
                }
            }

    }

    private fun MatrixCursor.putPlant(plant: Plant) {
        newRow()
            .add(PlantStatisticsContract.FIELD_PLANT_NAME, plant.name.value)
            .add(PlantStatisticsContract.FIELD_SPECIES_NAME, plant.speciesName)
            .add(PlantStatisticsContract.FIELD_PLANT_PICTURE, plant.plantPicture?.toString())
    }
}
