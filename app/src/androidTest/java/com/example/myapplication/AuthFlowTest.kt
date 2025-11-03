package com.example.myapplication

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.auth.AuthViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLoginAndLogout() {
        // For this test to pass, you need to have a user with these credentials in your Firebase project.
        val email = "test@example.com"
        val password = "password"

        // Enter email and password
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password
        ").performTextInput(password)

        // Click login button
        composeTestRule.onNodeWithText("Login").performClick()

        // Wait for the main screen to appear
        composeTestRule.waitForIdle()

        // Check if the greeting message is displayed
        composeTestRule.onNodeWithText("Hello $email!").assertExists()

        // Click logout button
        composeTestRule.onNodeWithText("Logout
        ").performClick()

        // Wait for the login screen to appear
        composeTestRule.waitForIdle()

        // Check if the login button is displayed
        composeTestRule.onNodeWithText("Login").assertExists()
    }
}
