package com.example.myapplication.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    init {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _authState.value = AuthState(user = currentUser)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(loading = true)
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(user = result.user)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(loading = true)
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(user = result.user)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState()
    }
}