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

    private val _navigateToAuth: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToAuth: LiveData<Event<Unit>> get() = _navigateToAuth

    private val _navigateToTasks: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToTasks: LiveData<Event<Unit>> get() = _navigateToTasks

    init {
        if (auth.currentUser == null) {
            _navigateToAuth.value = Event(Unit)
        } else {
            _navigateToTasks.value = Event(Unit)
        }
    }
}
