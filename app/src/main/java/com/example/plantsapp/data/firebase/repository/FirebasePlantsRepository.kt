package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.data.firebase.entity.FirebasePlant
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebasePlantsRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : PlantsRepository {

    private val plantsCollection = firestore.collection(KEY_COLLECTION_PLANTS)

    override fun observePlants(): Flow<List<Plant>> {
        return callbackFlow {
            val subscription = plantsCollection
                .addSnapshotListener { value, error ->
                    when {
                        error != null -> {
                            Timber.e("Listen failed..", error)
                        }

                        value != null -> {
                            val plants = value
                                .toObjects<FirebasePlant>()
                                .map { it.toPlant() }
                            trySend(plants)
                        }
                    }
                }

            awaitClose {
                subscription.remove()
            }
        }
    }

    override suspend fun fetchPlants(): List<Plant> {
        return plantsCollection
            .get()
            .await()
            .toObjects<FirebasePlant>()
            .map { it.toPlant() }
    }

    override suspend fun addPlant(plant: Plant) {
        // TODO add plantPicture to storage in Firebase and use its address here
        getPlantDocumentByName(plant.name)
            .set(FirebasePlant.from(plant))
            .await()
    }

    override suspend fun getPlantByName(name: Plant.Name): Plant {
        return getPlantDocumentByName(name)
            .get()
            .await()
            .toObject<FirebasePlant>()
            ?.toPlant() ?: throw NoSuchElementException("Unable to find plant with name = $name")
    }

    override suspend fun deletePlant(plant: Plant) {
        getPlantDocumentByName(plant.name)
            .delete()
            .await()
    }

    private fun getPlantDocumentByName(plantName: Plant.Name): DocumentReference {
        return plantsCollection.document(plantName.value)
    }

    companion object {
        // TODO maybe move this collection name somewhere
        private const val KEY_COLLECTION_PLANTS = "plants"
    }
}
