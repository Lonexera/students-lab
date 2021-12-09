package com.example.plantsapp.presentation.ui.authentication

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.R
import com.example.plantsapp.domain.usecase.AuthUseCase
import com.example.plantsapp.presentation.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    sealed class AuthResult {
        object NavigateToTasks : AuthResult()
        data class AuthError(val errorId: Int) : AuthResult()
    }

    private val _authResult: MutableLiveData<Event<AuthResult>> = MutableLiveData()
    val authResult: LiveData<Event<AuthResult>> get() = _authResult

    @Suppress("TooGenericExceptionCaught")
    fun onSignInResult(intent: Intent) {
        viewModelScope.launch {
            try {
                authUseCase(intent).apply {
                    Timber.d("User name - $name")
                    Timber.d("User profile picture url - $profilePicture")
                }

                _authResult.value = Event(AuthResult.NavigateToTasks)
            } catch (e: Exception) {
                Timber.e(e)
                _authResult.value = Event(AuthResult.AuthError(R.string.error_unable_to_sign_in))
            }
        }
    }
}
