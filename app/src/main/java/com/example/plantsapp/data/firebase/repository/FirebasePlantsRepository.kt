package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.data.firebase.entity.FirebasePlant
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebasePlantsRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : PlantsRepository {

    override fun observePlants(): Flow<List<Plant>> {
        val flowListPlants = MutableSharedFlow<List<Plant>>()
        firestore.collection(KEY_COLLECTION_PLANTS)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Timber.e("Listen failed..", error)
                    return@addSnapshotListener
                }

                value?.let { documents ->
                    // TODO change GlobalScope to some other scope
                    GlobalScope.launch {
                        val plants = documents
                            .toObjects<FirebasePlant>()
                            .map { it.toPlant() }

                        flowListPlants.emit(plants)
                    }
                }
            }

        return flowListPlants
    }

    override suspend fun fetchPlants(): List<Plant> {
        return firestore.collection(KEY_COLLECTION_PLANTS)
            .get()
            .await()
            .toObjects<FirebasePlant>()
            .map { it.toPlant() }
    }

    override suspend fun addPlant(plant: Plant) {
        // TODO add plantPicture to storage in Firebase and use its address here
        val firebasePlant = FirebasePlant.from(plant)

        firestore.collection(KEY_COLLECTION_PLANTS)
            .document(plant.name.value)
            .set(firebasePlant)
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully written!")
            }
            .addOnFailureListener {
                Timber.e("Error writing document", it)
            }
    }

    override suspend fun getPlantByName(name: Plant.Name): Plant {
        return firestore.collection(KEY_COLLECTION_PLANTS)
            .document(name.value)
            .get()
            .addOnFailureListener {
                Timber.e("Error reading document with Id = $name", it)
            }
            .await()
            .toObject<FirebasePlant>()!!
            .toPlant()
    }

    override suspend fun deletePlant(plant: Plant) {
        firestore.collection(KEY_COLLECTION_PLANTS)
            .document(plant.name.value)
            .delete()
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener {
                Timber.e("Error deleting document with Id = ${plant.name}", it)
            }
    }

    companion object {
        // TODO maybe move this collection name somewhere
        private const val KEY_COLLECTION_PLANTS = "plants"
    }
}
