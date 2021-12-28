package com.example.plantsapp.data.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.plantsapp.data.contentprovider.usecase.CreatePlantsCursorUseCase
import com.example.plantsapp.data.contentprovider.usecase.CreateTaskHistoryCursorUseCase
import com.example.plantsapp.data.contentprovider.usecase.CreateTasksCursorUseCase
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
        fun getCreatePlantsCursorUseCase(): CreatePlantsCursorUseCase
        fun getCreateTasksCursorUseCase(): CreateTasksCursorUseCase
        fun getCreateTaskHistoryCursorCase(): CreateTaskHistoryCursorUseCase
    }

    private lateinit var hiltEntryPoint: PlantsContentProviderEntryPoint
    private val createPlantsCursorUseCase: CreatePlantsCursorUseCase by lazy {
        hiltEntryPoint.getCreatePlantsCursorUseCase()
    }
    private val createTasksCursorUseCase: CreateTasksCursorUseCase by lazy {
        hiltEntryPoint.getCreateTasksCursorUseCase()
    }
    private val createTaskHistoryCursorUseCase: CreateTaskHistoryCursorUseCase by lazy {
        hiltEntryPoint.getCreateTaskHistoryCursorCase()
    }
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext
            ?: throw IllegalStateException("Unable to get applicationContext")

        hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            PlantsContentProviderEntryPoint::class.java
        )

        initializeUriMatcher()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        return when (uriMatcher.match(uri)) {
            PLANTS_URI_CODE -> runBlocking { createPlantsCursorUseCase() }
            TASKS_URI_CODE -> runBlocking {
                createTasksCursorUseCase(
                    plantName = selectionArgs?.first()
                        ?: throw IllegalArgumentException("Selection argument is required for this query")
                )
            }
            TASK_HISTORY_URI_CODE -> runBlocking {
                createTaskHistoryCursorUseCase(
                    plantName = selectionArgs?.first()
                        ?: throw IllegalArgumentException("Selection arguments are required for this query"),
                    taskKey = selectionArgs[1]
                )
            }
            else -> throw IllegalStateException("Provided uri is not supported")
        }
    }

    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            PLANTS_URI_CODE -> PlantStatisticsContract.Plants.CONTENT_TYPE
            TASKS_URI_CODE -> PlantStatisticsContract.Tasks.CONTENT_TYPE
            TASK_HISTORY_URI_CODE -> PlantStatisticsContract.TaskHistory.CONTENT_TYPE
            else -> throw IllegalStateException("Provided uri is not supported")
        }
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

    private fun initializeUriMatcher() {
        uriMatcher.addURI(
            PlantStatisticsContract.AUTHORITY,
            PlantStatisticsContract.Plants.PATH,
            PLANTS_URI_CODE
        )
        uriMatcher.addURI(
            PlantStatisticsContract.AUTHORITY,
            PlantStatisticsContract.Tasks.PATH,
            TASKS_URI_CODE
        )
        uriMatcher.addURI(
            PlantStatisticsContract.AUTHORITY,
            PlantStatisticsContract.TaskHistory.PATH,
            TASK_HISTORY_URI_CODE
        )
    }

    companion object {
        private const val PLANTS_URI_CODE = 0
        private const val TASKS_URI_CODE = 1
        private const val TASK_HISTORY_URI_CODE = 2
    }

    companion object {
        private const val PLANTS_URI_CODE = 0
        private const val TASKS_URI_CODE = 1
        private const val TASK_HISTORY_URI_CODE = 2
    }
}
