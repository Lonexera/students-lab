package com.example.plantsapp.domain.repository

import com.example.plantsapp.domain.model.User

interface UserRepository {
    suspend fun setUser(user: User)
    fun requireUser(): User
    fun isUserCached(): Boolean
    suspend fun clearUser()
}
