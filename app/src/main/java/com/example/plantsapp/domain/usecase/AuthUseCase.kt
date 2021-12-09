package com.example.plantsapp.domain.usecase

import android.content.Intent
import com.example.plantsapp.domain.model.User

interface AuthUseCase {
    suspend operator fun invoke(intent: Intent): User
}
