package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.data.firebase.entity.FirebaseTaskCompletion
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import com.example.plantsapp.domain.repository.TasksHistoryRepository
import com.example.plantsapp.presentation.ui.utils.atEndDay
import com.example.plantsapp.presentation.ui.utils.atStartDay
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirebaseTaskHistoryRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : TasksHistoryRepository {

    override suspend fun createTaskCompletion(plant: Plant, task: Task, completionDate: Date) {
        getTaskHistoryCollection(plant, task)
            .add(FirebaseTaskCompletion(completionDate))
            .await()
    }

    override suspend fun isTaskCompletedForDate(plant: Plant, task: Task, date: Date): Boolean {
        return getTaskHistoryCollection(plant, task)
            .whereGreaterThan("completionDate", date.atStartDay())
            .whereLessThan("completionDate", date.atEndDay())
            .get()
            .await()
            .size() > 0
    }

    private fun getTaskHistoryCollection(plant: Plant, task: Task): CollectionReference {
        return firestore
            .collection(KEY_COLLECTION_PLANTS)
            .document(plant.name.value)
            .collection(KEY_COLLECTION_TASKS)
            .document(TaskKeys.from(task).key)
            .collection(KEY_COLLECTION_TASK_HISTORY)
    }

    companion object {
        // TODO maybe move this collection name somewhere
        private const val KEY_COLLECTION_TASK_HISTORY = "taskHistory"
        private const val KEY_COLLECTION_TASKS = "tasks"
        private const val KEY_COLLECTION_PLANTS = "plants"
    }
}
