package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserRepository @Inject constructor() : UserRepository {

    private var user: User? = null

    override fun setUser(user: User) {
        this.user = user
    }

    override fun requireUser(): User = user ?: throw IllegalStateException("User was not authorized!")

    override fun isAuthorized(): Boolean = (user != null)
}
