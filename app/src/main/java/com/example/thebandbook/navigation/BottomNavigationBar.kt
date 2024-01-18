package com.example.thebandbook.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.thebandbook.ui.theme.TheBandBookTheme
import java.util.Locale


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Calendar,
        Screen.Forum
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    TheBandBookTheme {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background
        ) {
            items.forEach { screen ->

                val isSelected = featureGroups.any {
                    it.value.contains(currentRoute) && it.value.contains(screen.route)
                }

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconResourceId),
                            contentDescription = screen.route,
                            tint = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.DarkGray,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(screen.route.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        })
                    },
                    selected = isSelected || currentRoute == screen.route, //navController.currentBackStackEntryAsState().value?.destination?.route == screen.route,
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
}

val featureGroups = mapOf(
    "Calendar" to listOf(AppRoutes.CALENDAR, AppRoutes.CREATE_EVENT_SCREEN),
    "Forum" to listOf(AppRoutes.FORUM, AppRoutes.FORUM_CREATE_THREAD, AppRoutes.FORUM_VIEW_THREAD),
    "Dashboard" to listOf(AppRoutes.DASHBOARD, AppRoutes.SIGN_UP_SCREEN)
)
