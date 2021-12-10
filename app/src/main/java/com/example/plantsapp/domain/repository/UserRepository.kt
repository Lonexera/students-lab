package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.User

interface UserRepository {
    val user: User?

    fun setUser(user: User)
    fun requireUser(): User
    fun isAuthorized(): Boolean
}
