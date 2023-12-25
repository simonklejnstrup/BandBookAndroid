package com.example.thebandbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.thebandbook.ui.theme.TheBandBookTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thebandbook.navigation.AppRoutes
import com.example.thebandbook.presentation.screens.DashboardScreen
import com.example.thebandbook.presentation.screens.ForumCreateEventScreen
import com.example.thebandbook.presentation.screens.ForumScreen
import com.example.thebandbook.presentation.screens.CalendarScreen
import com.example.thebandbook.presentation.common.BottomNavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheBandBookTheme {
                AppContent()


//                val navController = rememberNavController()
//                NavHost(navController = navController, startDestination = "dashboard") {
//                    composable(AppRoutes.DASHBOARD) { DashboardScreen() }
//                    composable(AppRoutes.FORUM_OVERVIEW) { ForumOverviewScreen(navController) }
//                    composable(AppRoutes.FORUM_CREATE_EVENT) { ForumCreateEventScreen(navController) }
//                    navigation(
//                        startDestination = "login",
//                        route = "auth"
//                    ) {
//                        composable("login") {
//                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
//                        }
//                        composable("register") {
//                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
//                        }
//                        composable("forgot_password") {
//                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
//                        }
//                    }
//                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {

        NavHost(navController, startDestination = AppRoutes.DASHBOARD) {
            composable(AppRoutes.CALENDAR) { CalendarScreen() }
            composable(AppRoutes.FORUM) { ForumScreen() }
            composable(AppRoutes.DASHBOARD) { DashboardScreen() }
            composable(AppRoutes.FORUM_CREATE_EVENT) { ForumCreateEventScreen(navController) }
        }
    }
}
