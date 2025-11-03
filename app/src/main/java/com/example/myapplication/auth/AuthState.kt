package com.example.myapplication.auth

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val user: FirebaseUser? = null,
    val loading: Boolean = false,
    val error: String? = null
)
