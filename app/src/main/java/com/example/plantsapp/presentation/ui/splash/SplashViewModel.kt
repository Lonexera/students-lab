package com.example.plantsapp.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.presentation.core.Event
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    enum class Directions {
        AUTH_SCREEN,
        TASKS_SCREEN
    }

    private val _navigate: MutableLiveData<Event<Directions>> = MutableLiveData()
    val navigate: LiveData<Event<Directions>> get() = _navigate

    init {
        if (auth.currentUser == null) {
            _navigate.value = Event(Directions.AUTH_SCREEN)
        } else {
            _navigate.value = Event(Directions.TASKS_SCREEN)
        }
    }
}
