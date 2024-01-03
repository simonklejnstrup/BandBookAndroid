package com.example.thebandbook

import android.app.Activity.RESULT_OK
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thebandbook.ui.theme.TheBandBookTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.presentation.screens.dashboard.DashboardScreen
import com.example.thebandbook.presentation.screens.forum.ForumCreateThreadScreen
import com.example.thebandbook.presentation.screens.forum.ForumScreen
import com.example.thebandbook.presentation.screens.calendar.CalendarScreen
import com.example.thebandbook.navigation.BottomNavigationBar
import com.example.thebandbook.presentation.firebase.sign_in.GoogleAuthClient
import com.example.thebandbook.presentation.screens.authentication.SignInScreen
import com.example.thebandbook.presentation.screens.calendar.CreateEventScreen
import com.example.thebandbook.presentation.screens.authentication.SignUpScreen
import com.example.thebandbook.presentation.viewmodels.FirebaseSignInViewmodel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheBandBookTheme {
//                AppContent()

                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { padding ->

                    NavHost(navController, startDestination = AppRoutes.SIGN_IN) {
                        composable(AppRoutes.CALENDAR) { CalendarScreen(navController) }
                        composable(AppRoutes.CREATE_EVENT_SCREEN) { CreateEventScreen(navController) }
                        composable(AppRoutes.FORUM) { ForumScreen(navController) }
                        composable(AppRoutes.FORUM_CREATE_THREAD) { ForumCreateThreadScreen() }
                        composable(AppRoutes.DASHBOARD) {
                            if (googleAuthUiClient.getSignedInUser() != null)
                                googleAuthUiClient.getSignedInUser()
                            DashboardScreen(
                                navController = navController,
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign out",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.popBackStack()
                                    }
                                },
                                modifier = Modifier.padding(padding))
                        }
                        composable(AppRoutes.SIGN_UP_SCREEN) { SignUpScreen() }
                        composable(AppRoutes.SIGN_IN) {
                            val viewModel = viewModel<FirebaseSignInViewmodel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate(AppRoutes.DASHBOARD)
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSingInResult(signInResult)
                                        }
                                    }
                                })

                            LaunchedEffect(key1 = state.isSignInSuccesful) {
                                if (state.isSignInSuccesful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(AppRoutes.DASHBOARD)
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onGoogleSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
//    val navController = rememberNavController()
//    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) }
//    ) {
//
//        NavHost(navController, startDestination = AppRoutes.DASHBOARD) {
//            composable(AppRoutes.CALENDAR) { CalendarScreen(navController) }
//            composable(AppRoutes.CREATE_EVENT_SCREEN) { CreateEventScreen(navController) }
//            composable(AppRoutes.FORUM) { ForumScreen(navController) }
//            composable(AppRoutes.FORUM_CREATE_THREAD) { ForumCreateThreadScreen() }
//            composable(AppRoutes.DASHBOARD) { DashboardScreen(navController = navController) }
//            composable(AppRoutes.SIGN_UP_SCREEN) { SignUpScreen() }
//            composable(AppRoutes.SIGN_IN) {
//                val viewModel = viewModel<FirebaseSignInViewmodel>()
//                val state by viewModel.state.collectAsStateWithLifecycle()
//
//                val launcher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.StartIntentSenderForResult(),
//                    onResult = { result ->
//                        if(result.resultCode == RESULT_OK) {
//                            lifecycleScope.launch {
//                                val signInResult = googleAuth
//                            }
//                        }
//                    })
//            }
//        }
//    }
}
