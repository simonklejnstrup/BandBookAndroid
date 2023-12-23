package com.example.thebandbook.presentation.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.thebandbook.navigation.Screen
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.capitalize
import androidx.navigation.compose.currentBackStackEntryAsState
import java.util.Locale


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Calendar,
        Screen.Forum
    )
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {  }, // Replace with appropriate icons
                label = { Text(screen.route.replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()) else it.toString() }) },
                selected = navController.currentBackStackEntryAsState().value?.destination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
