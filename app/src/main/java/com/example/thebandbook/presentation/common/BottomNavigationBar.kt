package com.example.thebandbook.presentation.common

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
import com.example.thebandbook.navigation.Screen
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

                val isSelected = currentRoute == screen.route

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
}
