@file:Suppress("WildcardImport", "NoWildcardImports")
package com.example.plantsapp.data.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.example.plantstatscontract.*
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

    private val plantsRepository: PlantsRepository by lazy {
        val appContext = context?.applicationContext
            ?: throw IllegalStateException("Unable to get applicationContext")

        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            PlantsContentProviderEntryPoint::class.java
        )
        hiltEntryPoint.plantsRepository()
    }

    override fun onCreate(): Boolean {
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
            CONTENT_URI -> {
                MatrixCursor(
                    arrayOf(
                        FIELD_PLANT_NAME,
                        FIELD_SPECIES_NAME,
                        FIELD_PLANT_PICTURE
                    )
                )
                    .apply {
                        runBlocking {
                            putPlants(
                                plants = plantsRepository.fetchPlants()
                            )
                        }
                    }
            }
            else -> throw IllegalStateException("Provided uri is not supported")
        }
    }

    override fun getType(uri: Uri): String {
        return CONTENT_TYPE
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

    private fun MatrixCursor.putPlants(plants: List<Plant>): Cursor = apply {
        plants.forEach {
            newRow()
                .add(FIELD_PLANT_NAME, it.name.value)
                .add(FIELD_SPECIES_NAME, it.speciesName)
                .add(FIELD_PLANT_PICTURE, it.plantPicture?.toString())
        }
    }
}
