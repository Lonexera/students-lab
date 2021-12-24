package com.example.plantsapp.presentation.ui.authentication

import androidx.annotation.StringRes
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
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    sealed class AuthResult {
        object NavigateToTasks : AuthResult()
        data class AuthError(@StringRes val errorId: Int) : AuthResult()
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _authResult: MutableLiveData<Event<AuthResult>> = MutableLiveData()
    val authResult: LiveData<Event<AuthResult>> get() = _authResult

    fun onSignInResult(token: String?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                if (token == null) {
                    _authResult.value =
                        Event(AuthResult.AuthError(R.string.error_unable_to_sign_in))
                    return@launch
                }

                val event = try {
                    authUseCase(AuthUseCase.AuthInput(token))
                    AuthResult.NavigateToTasks
                } catch (e: IllegalStateException) {
                    Timber.e(e)
                    AuthResult.AuthError(R.string.error_unable_to_sign_in)
                }
                _authResult.value = Event(event)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
