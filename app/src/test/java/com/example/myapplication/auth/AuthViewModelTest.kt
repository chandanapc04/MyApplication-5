package com.example.myapplication.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import com.google.android.gms.tasks.Task
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        firebaseAuth = Mockito.mock(FirebaseAuth::class.java)
        authViewModel = AuthViewModel()
        // Manually inject the mock through a private field, as there's no public setter
        val authField = authViewModel.javaClass.getDeclaredField("auth")
        authField.isAccessible = true
        authField.set(authViewModel, firebaseAuth)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test login success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val authResult = Mockito.mock(AuthResult::class.java)
        val firebaseUser = Mockito.mock(FirebaseUser::class.java)

        val task = Mockito.mock(Task::class.java) as Task<AuthResult>
        whenever(task.isSuccessful).thenReturn(true)
        whenever(task.result).thenReturn(authResult)
        whenever(authResult.user).thenReturn(firebaseUser)
        whenever(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(task)

        authViewModel.login(email, password)
        testScheduler.advanceUntilIdle()

        assertEquals(firebaseUser, authViewModel.authState.value.user)
        assertNull(authViewModel.authState.value.error)
    }

    @Test
    fun `test signup success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val authResult = Mockito.mock(AuthResult::class.java)
        val firebaseUser = Mockito.mock(FirebaseUser::class.java)

        val task = Mockito.mock(Task::class.java) as Task<AuthResult>
        whenever(task.isSuccessful).thenReturn(true)
        whenever(task.result).thenReturn(authResult)
        whenever(authResult.user).thenReturn(firebaseUser)
        whenever(firebaseAuth.createUserWithEmailAndPassword(email, password)).thenReturn(task)

        authViewModel.signUp(email, password)
        testScheduler.advanceUntilIdle()

        assertEquals(firebaseUser, authViewModel.authState.value.user)
        assertNull(authViewModel.authState.value.error)
    }

    @Test
    fun `test logout`() = runTest {
        authViewModel.logout()
        testScheduler.advanceUntilIdle()

        assertNull(authViewModel.authState.value.user)
    }
}
