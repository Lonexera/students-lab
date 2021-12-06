package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.data.firebase.entity.FirebaseTask
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.repository.TasksRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirebaseTasksRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : TasksRepository {

    private val plantsCollection = firestore.collection(KEY_COLLECTION_PLANTS)

    override suspend fun addTasks(plant: Plant, tasks: List<Task>) {
        tasks.forEach {
            getTasksCollectionForPlant(plant)
                .document(TaskKeys.from(it).key)
                .set(FirebaseTask.from(plant, task = it))
                .await()
        }
    }

    override suspend fun getTasksForPlant(plant: Plant): List<Task> {
        return getTasksCollectionForPlant(plant)
            .get()
            .await()
            .map { documentSnapshot ->
                documentSnapshot
                    .toObject<FirebaseTask>()
                    .toTask(
                        taskId = documentSnapshot.id
                    )
            }
    }

    override suspend fun updateTask(task: Task, completionDate: Date) {
        getTasksCollectionForPlant(plant)
            .document(TaskKeys.from(task).key)
            .update("lastUpdateDate", completionDate)
            .await()
    }

    private fun getTasksCollectionForPlant(plant: Plant): CollectionReference {
        return plantsCollection
            .document(plant.name.value)
            .collection(KEY_COLLECTION_TASKS)
    }

    companion object {
        // TODO maybe move this collection name somewhere
        private const val KEY_COLLECTION_TASKS = "tasks"
        private const val KEY_COLLECTION_PLANTS = "plants"
    }
}
