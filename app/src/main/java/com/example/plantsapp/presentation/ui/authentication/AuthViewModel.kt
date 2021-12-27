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
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    sealed class AuthState {
        object Loading : AuthState()
        object NavigateToTasks : AuthState()
        data class AuthError(@StringRes val errorId: Int) : AuthState()
    }

    private val _authState: MutableLiveData<Event<AuthState>> = MutableLiveData()
    val authState: LiveData<Event<AuthState>> get() = _authState

    @Suppress("TooGenericExceptionCaught")
    fun onSignInResult(token: String?) {
        viewModelScope.launch {
            if (token == null) {
                _authState.value =
                    Event(AuthState.AuthError(R.string.error_unable_to_sign_in))
                return@launch
            }

            val event = try {
                _authState.value = Event(AuthState.Loading)
                authUseCase(AuthUseCase.AuthInput(token))
                AuthState.NavigateToTasks
            } catch (e: Exception) {
                Timber.e(e)
                AuthState.AuthError(R.string.error_unable_to_sign_in)
            }

            _authState.value = Event(event)
        }
    }
}
