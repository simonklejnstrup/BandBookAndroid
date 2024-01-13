package com.example.thebandbook.authentication.firebase.sign_in

data class SignInResult(
    val data: FirebaseUserData?,
    val errorMessage: String?
)


data class FirebaseUserData(
    val userId: String?,
    val email: String?,
    val name: String?,
    val profilePictureUrl: String?
)
