package com.example.thebandbook.authentication

import android.content.Context
import com.example.thebandbook.authentication.firebase.sign_in.GoogleAuthClient
import com.google.android.gms.auth.api.identity.Identity


object GoogleAuthClientSingleton {
    lateinit var googleAuthUiClient: GoogleAuthClient
        private set

    fun initialize(applicationContext: Context) {
        if (!::googleAuthUiClient.isInitialized) {
            googleAuthUiClient = GoogleAuthClient(
                context = applicationContext,
                oneTapClient = Identity.getSignInClient(applicationContext)
            )
        }
    }
}