package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.AuthScreen
import com.example.myapplication.auth.AuthViewModel
import com.example.myapplication.premium.PremiumScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val authState by authViewModel.authState.collectAsState()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (authState.user != null) {
                        NavHost(navController = navController, startDestination = "main") {
                            composable("main") {
                                Greeting(
                                    name = authState.user?.email ?: "Android",
                                    modifier = Modifier.padding(innerPadding),
                                    onLogout = { authViewModel.logout() },
                                    onGoToPremium = { navController.navigate("premium") }
                                )
                            }
                            composable("premium") {
                                PremiumScreen()
                            }
                        }
                    } else {
                        AuthScreen(authViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onLogout: () -> Unit, onGoToPremium: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
        )
        Button(onClick = onLogout, modifier = Modifier.padding(top = 16.dp)) {
            Text("Logout")
        }
        Button(onClick = onGoToPremium, modifier = Modifier.padding(top = 8.dp)) {
            Text("Go to Premium")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android", onLogout = {}, onGoToPremium = {})
    }
}
