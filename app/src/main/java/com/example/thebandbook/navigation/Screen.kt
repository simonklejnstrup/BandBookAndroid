package com.example.thebandbook.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Calendar : Screen("calendar")
    object Forum : Screen("forum")
}
