package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.data.firebase.entity.FirebaseTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.repository.TasksRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirebaseTasksRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : TasksRepository {

    private val plantsCollection = firestore.collection(KEY_COLLECTION_PLANTS)

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        tasks.forEach { task ->
            getTaskDocument(task, plant)
                .set(FirebaseTask.from(plant, task))
                .await()
        }
    }

    override suspend fun getTasksForPlant(plant: Plant): List<Task> {
        return getTasksCollectionForPlant(plant)
            .get()
            .await()
            .toObjects<FirebaseTask>()
            .map { it.toTask() }
    }

    override suspend fun updateTask(plant: Plant, task: Task, completionDate: Date) {
        getTaskDocument(task, plant)
            .update(FIELD_LAST_UPDATE_DATE, completionDate)
            .await()
    }

    private fun getTasksCollectionForPlant(plant: Plant): CollectionReference {
        return plantsCollection
            .document(plant.name.value)
            .collection(KEY_COLLECTION_TASKS)
    }

    private fun getTaskDocument(task: Task, plant: Plant): DocumentReference {
        return getTasksCollectionForPlant(plant)
            .document(TaskKeys.from(task).key)
    }

    companion object {
        // TODO maybe move this collection name somewhere
        private const val KEY_COLLECTION_TASKS = "tasks"
        private const val KEY_COLLECTION_PLANTS = "plants"
        private const val FIELD_LAST_UPDATE_DATE = "lastUpdateDate"
    }
}
