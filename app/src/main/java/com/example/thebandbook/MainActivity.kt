package com.example.thebandbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thebandbook.authentication.AuthenticatedScreenWrapper
import com.example.thebandbook.authentication.GoogleAuthClientSingleton
import com.example.thebandbook.authentication.GoogleAuthClientSingleton.googleAuthUiClient
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.navigation.BottomNavigationBar
import com.example.thebandbook.presentation.screens.authentication.SignInScreen
import com.example.thebandbook.presentation.screens.authentication.SignUpScreen
import com.example.thebandbook.presentation.screens.calendar.CalendarScreen
import com.example.thebandbook.presentation.screens.calendar.CreateEventScreen
import com.example.thebandbook.presentation.screens.dashboard.DashboardScreen
import com.example.thebandbook.presentation.screens.forum.ForumCreateThreadScreen
import com.example.thebandbook.presentation.screens.forum.ForumScreen
import com.example.thebandbook.ui.theme.TheBandBookTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GoogleAuthClientSingleton.initialize(applicationContext)
        setContent {
            TheBandBookTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { _ ->

                    fun NavGraphBuilder.appComposable(
                        route: String,
                        content: @Composable (NavController) -> Unit
                    ) {
                        composable(route) { content(navController) }
                    }

                    NavHost(navController, startDestination = AppRoutes.DASHBOARD) {
                        appComposable(AppRoutes.CALENDAR) { navController ->
                            AuthenticatedScreenWrapper(navController) {
                                CalendarScreen(navController)
                            }
                        }
                        appComposable(AppRoutes.CREATE_EVENT_SCREEN) { navController ->
                            CreateEventScreen(
                                navController
                            )
                        }
                        appComposable(AppRoutes.FORUM) { navController ->
                            AuthenticatedScreenWrapper(navController) {
                                ForumScreen(navController)
                            }
                        }
                        appComposable(AppRoutes.FORUM_CREATE_THREAD) { navController -> ForumCreateThreadScreen(navController = navController) }
                        appComposable(AppRoutes.DASHBOARD) { navController ->
                            AuthenticatedScreenWrapper(navController) {
                                DashboardScreen(
                                    navController = navController,
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            googleAuthUiClient.signOut()
                                            navController.popBackStack()
                                        }
                                    }
                                )
                            }
                        }
                        appComposable(AppRoutes.SIGN_UP_SCREEN) { SignUpScreen() }
                        appComposable(AppRoutes.SIGN_IN) {
                            SignInScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}