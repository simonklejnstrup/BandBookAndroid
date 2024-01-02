package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.thebandbook.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewmodel: ViewModel() {

    private val _newUserData = MutableStateFlow(NewUserData())
    open val newUserData = _newUserData.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun setFirstname(firstname: String) {
        _newUserData.value = newUserData.value.copy(firstname = firstname)
    }

    fun setLastname(lastname: String) {
        _newUserData.value = newUserData.value.copy(lastname = lastname)
    }

    fun setEmail(email: String) {
        _newUserData.value = newUserData.value.copy(email = email)
    }
    fun setPassword(password: String) {
        _newUserData.value = newUserData.value.copy(password = password)
    }
    fun setBand(band: String) {
        _newUserData.value = newUserData.value.copy(band = band)
    }

    fun onSignUpPressed() {
        println("_newUserData.value: ${_newUserData.value}")
    }


    // Internal data class to hold the event data
    data class NewUserData(
        val firstname: String = "",
        val lastname: String = "",
        val email: String = "",
        val password: String = "",
        val band: String = "",
    )
}