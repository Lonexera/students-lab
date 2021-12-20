package com.example.plantsapp.data.usecase

import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.repository.UserRepository
import com.example.plantsapp.domain.usecase.SignOutUseCase
import com.example.plantsapp.domain.workmanager.TasksWorkManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @FirebaseQualifier private val userRepository: UserRepository,
    private val googleSignInClient: GoogleSignInClient,
    private val tasksWorkManager: TasksWorkManager
) : SignOutUseCase {

    override suspend fun invoke() {
        auth.signOut()
        googleSignInClient.signOut().await()
        userRepository.clearUser()
        tasksWorkManager.cancelAllWork()
    }
}
