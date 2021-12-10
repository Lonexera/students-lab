package com.example.plantsapp.domain.usecase

import com.example.plantsapp.domain.model.User

interface AuthUseCase {

    @JvmInline
    value class AuthInput(val token: String)

    suspend operator fun invoke(input: AuthInput): User
}
