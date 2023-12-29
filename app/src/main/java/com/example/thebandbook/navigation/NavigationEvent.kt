package com.example.thebandbook.navigation

sealed class NavigationEvent {
    object NavigateBack: NavigationEvent()
    data class NavigateToDetails(val id: Int): NavigationEvent()
    // Add other navigation events as needed
}
