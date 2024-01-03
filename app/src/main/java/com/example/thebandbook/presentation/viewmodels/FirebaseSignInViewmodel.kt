package com.example.thebandbook.presentation.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.thebandbook.presentation.firebase.sign_in.SignInResult
import com.example.thebandbook.presentation.firebase.sign_in.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FirebaseSignInViewmodel: ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSingInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccesful = result.data != null,
            signInErrorMessage = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}