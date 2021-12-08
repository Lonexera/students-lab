package com.example.plantsapp.presentation.ui.authentication

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsapp.R
import com.example.plantsapp.presentation.core.Event
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _navigateToSplash: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToSplash: LiveData<Event<Unit>> get() = _navigateToSplash
    private val _signingError: MutableLiveData<Int> = MutableLiveData()
    val signingError: LiveData<Int> get() = _signingError

    fun onSignInResult(intent: Intent) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {
            val account = task.getResult(ApiException::class.java)
            signInFirebase(account.idToken!!)
        } catch (e: ApiException) {
            Timber.e(e)
            _signingError.value = R.string.error_unable_to_sign_in
        }
    }

    private fun signInFirebase(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _navigateToSplash.value = Event(Unit)
                } else {
                    _signingError.value = R.string.error_unable_to_sign_in
                }
            }
    }
}
