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
import com.example.thebandbook.presentation.screens.dashboard.DashboardScreen
import com.example.thebandbook.presentation.screens.forum.ForumCreateThreadScreen
import com.example.thebandbook.presentation.screens.forum.ForumScreen
import com.example.thebandbook.presentation.screens.calendar.CalendarScreen
import com.example.thebandbook.navigation.BottomNavigationBar
import com.example.thebandbook.presentation.screens.calendar.CreateEventScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheBandBookTheme {
                AppContent()
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
            composable(AppRoutes.CALENDAR) { CalendarScreen(navController) }
            composable(AppRoutes.CREATE_EVENT_SCREEN) { CreateEventScreen(navController) }
            composable(AppRoutes.FORUM) { ForumScreen(navController) }
            composable(AppRoutes.FORUM_CREATE_THREAD) { ForumCreateThreadScreen() }
            composable(AppRoutes.DASHBOARD) { DashboardScreen() }
        }
    }
}
