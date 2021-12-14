package com.example.plantsapp.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.model.User
import com.example.plantsapp.domain.repository.UserRepository
import com.example.plantsapp.domain.usecase.SignOutUseCase
import com.example.plantsapp.presentation.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    @FirebaseQualifier private val userRepository: UserRepository
) : ViewModel() {

    val user: LiveData<User> = MutableLiveData(userRepository.requireUser())

    private val _navigateToAuth: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToAuth: LiveData<Event<Unit>> get() = _navigateToAuth

    fun onSignOutClick() {
        viewModelScope.launch {
            signOutUseCase()
            _navigateToAuth.value = Event(Unit)
        }
    }
}
