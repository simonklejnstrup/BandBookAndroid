package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.thebandbook.authentication.firebase.sign_in.SignInResult
import com.example.thebandbook.authentication.firebase.sign_in.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FirebaseSignInViewmodel: ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun onSingInResult(result: SignInResult) {
        _signInState.update { it.copy(
            isSignInSuccesful = result.data != null,
            signInErrorMessage = result.errorMessage
        ) }
    }

    fun resetState() {
        _signInState.update { SignInState() }
    }
}