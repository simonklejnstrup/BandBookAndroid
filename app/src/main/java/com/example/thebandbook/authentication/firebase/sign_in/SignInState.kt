package com.example.thebandbook.authentication.firebase.sign_in

data class SignInState(
    val isSignInSuccesful: Boolean = false,
    val signInErrorMessage: String? = null
)
