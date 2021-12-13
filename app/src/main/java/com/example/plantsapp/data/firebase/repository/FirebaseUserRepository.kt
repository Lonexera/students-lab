package com.example.plantsapp.data.firebase.repository

import android.content.Context
import com.example.plantsapp.data.firebase.utils.user
import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : UserRepository {

    private val sharedPrefs = context
            .getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    private var user: User? = sharedPrefs?.user

    override suspend fun setUser(user: User) {
        sharedPrefs.user = user
        this.user = user
    }

    override fun requireUser(): User =
        user ?: throw IllegalStateException("User was not authorized!")

    override fun isUserCached(): Boolean = (user != null)

    override suspend fun clearUser() {
        sharedPrefs.user = null
        user = null
    }

    companion object {
        private const val PREFERENCES_FILE_KEY = "com.example.plantsApp.PREFERENCE_FILE_KEY"
    }
}
