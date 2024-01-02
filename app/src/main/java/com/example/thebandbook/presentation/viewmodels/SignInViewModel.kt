package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebandbook.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    private val _signInData = MutableStateFlow(SignInData())
    val signInData = _signInData.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    fun setPassword(password: String) {
        _signInData.value = signInData.value.copy(password = password)
    }

    fun setEmail(email: String) {
        _signInData.value = signInData.value.copy(email = email)
    }

    fun navigateToSignUpScreen() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToSignUp)
        }
    }

    fun onSignInPressed() {
        println("_signInData.value: ${_signInData.value}")
    }

    data class SignInData(
        val email: String = "",
        val password: String = "",
    )
}