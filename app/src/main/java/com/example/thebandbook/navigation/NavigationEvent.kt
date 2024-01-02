package com.example.thebandbook.navigation

sealed class NavigationEvent {
    object NavigateBack: NavigationEvent()
    object NavigateToSignUp: NavigationEvent()
//    data class NavigateToDetails(val id: Int): NavigationEvent()
}
