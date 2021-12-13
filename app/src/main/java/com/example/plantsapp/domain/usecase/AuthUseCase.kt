package com.example.plantsapp.domain.usecase

interface AuthUseCase {

    @JvmInline
    value class AuthInput(val token: String)

    suspend operator fun invoke(input: AuthInput)
}
