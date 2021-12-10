package com.example.plantsapp.data.firebase.repository

import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserRepository @Inject constructor() : UserRepository {

    override var user: User? = null
    private set

    override fun setUser(user: User) {
        this.user = user
    }

    override fun requireUser(): User = user!!

    override fun isAuthorized(): Boolean = (user != null)
}
