package com.example.thebandbook.navigation

import androidx.annotation.DrawableRes
import com.example.thebandbook.R

sealed class Screen(val route: String, @DrawableRes val iconResourceId: Int) {
    object Dashboard : Screen("dashboard", R.drawable.ic_dashboard)
    object Calendar : Screen("calendar", R.drawable.ic_calendar)
    object Forum : Screen("forum", R.drawable.ic_forum)
}

