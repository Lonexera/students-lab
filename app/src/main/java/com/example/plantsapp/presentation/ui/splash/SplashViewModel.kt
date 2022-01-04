package com.example.plantsapp.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.di.module.FirebaseQualifier
import com.example.plantsapp.domain.repository.UserRepository
import com.example.plantsapp.uicore.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @FirebaseQualifier private val userRepository: UserRepository
) : ViewModel() {

    enum class Directions {
        AUTH_SCREEN,
        TASKS_SCREEN
    }

    private val _navigate: MutableLiveData<Event<Directions>> = MutableLiveData()
    val navigate: LiveData<Event<Directions>> get() = _navigate

    init {
        val event = if (userRepository.isUserCached()) {
            Directions.TASKS_SCREEN
        } else {
            Directions.AUTH_SCREEN
        }

        _navigate.value = Event(event)
    }
}
