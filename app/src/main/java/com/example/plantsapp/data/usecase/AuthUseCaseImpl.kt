package com.example.plantsapp.data.usecase

import android.content.Intent
import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.usecase.AuthUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.lang.IllegalStateException
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthUseCase {

    @Throws(IllegalStateException::class)
    override suspend fun invoke(intent: Intent): User {
        val account = GoogleSignIn
            .getSignedInAccountFromIntent(intent)
            .getResult(ApiException::class.java)

        return getSignedInFirebaseUser(account.idToken!!)
            ?.let {
                User(
                    name = it.displayName!!,
                    profilePicture = it.photoUrl
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
