package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.data.firebase.utils.toUser
import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserRepository @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {

    private var user: User? = auth.currentUser?.toUser()

    override fun setUser(user: User) {
        this.user = user
    }

    override fun requireUser(): User = user ?: throw IllegalStateException("User was not authorized!")

    override fun isAuthorized(): Boolean = (user != null)
}
