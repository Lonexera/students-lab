package com.example.plantsapp.data.firebase.repository

import android.net.Uri
import com.example.plantsapp.data.firebase.entity.FirebasePlantPhoto
import com.example.plantsapp.data.firebase.utils.addImage
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.repository.PlantPhotosRepository
import com.example.plantsapp.domain.repository.UserRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Date
import javax.inject.Inject

class FirestorePlantPhotosRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @FirebaseQualifier private val userRepository: UserRepository
) : PlantPhotosRepository {

    private val storageRef = storage.reference

    override suspend fun savePhoto(plant: Plant, photoUri: Uri) {
        val photoUrl = storageRef.addImage(
            user = userRepository.requireUser(),
            plant = plant,
            picture = photoUri
        )

        saveImageUrlInDatabase(photoUrl, plant, Date())
    }

    private fun saveImageUrlInDatabase(photoUri: Uri, plant: Plant, creationDate: Date) {
        getPhotosCollection(plant)
            .document(creationDate.toString())
            .set(
                FirebasePlantPhoto.from(photoUri, creationDate)
            )
    }

    private fun getPhotosCollection(plant: Plant): CollectionReference {
        return firestore
            .collection(KEY_COLLECTION_USERS)
            .document(userRepository.requireUser().uid)
            .collection(KEY_COLLECTION_PLANTS)
            .document(plant.name.value)
            .collection(KEY_COLLECTION_PHOTOS)
    }

    companion object {
        private const val KEY_COLLECTION_USERS = "users"
        private const val KEY_COLLECTION_PLANTS = "plants"
        private const val KEY_COLLECTION_PHOTOS = "photos"
    }
}
