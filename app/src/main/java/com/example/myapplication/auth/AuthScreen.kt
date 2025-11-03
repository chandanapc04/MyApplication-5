package com.example.myapplication.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthScreen(authViewModel: AuthViewModel = viewModel()) {
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp)
        )

        if (isLogin) {
            Button(
                onClick = { authViewModel.login(email, password) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Login")
            }
        } else {
            Button(
                onClick = { authViewModel.signUp(email, password) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Sign Up")
            }
        }

        TextButton(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "Don't have an account? Sign up" else "Already have an account? Log in")
        }

        authState.error?.let {
            Text("Error: $it")
        }
    }
}
