package com.example.plantsapp.data.usecase

import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.repository.UserRepository
import com.example.plantsapp.domain.usecase.AuthUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.lang.IllegalStateException
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @FirebaseQualifier private val userRepository: UserRepository
) : AuthUseCase {

    @Throws(IllegalStateException::class)
    override suspend fun invoke(input: AuthUseCase.AuthInput) {
        getSignedInFirebaseUser(input.token)
            ?.let {
                userRepository.setUser(
                    User(
                        uid = it.uid,
                        name = it.displayName ?: "No Name",
                        profilePicture = it.photoUrl
                    )
                )
            } ?: throw IllegalStateException("Cannot sign in")
    }

    private suspend fun getSignedInFirebaseUser(idToken: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
            .await()
            .user
    }
}
