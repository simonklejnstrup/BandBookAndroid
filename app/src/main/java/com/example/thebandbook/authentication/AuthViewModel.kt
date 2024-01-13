package com.example.thebandbook.authentication

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val googleAuthUiClient = GoogleAuthClientSingleton.googleAuthUiClient
    // This function checks if the user is authenticated
    fun isUserAuthenticated(): Boolean {
        return googleAuthUiClient.getSignedInUser() != null
    }
}