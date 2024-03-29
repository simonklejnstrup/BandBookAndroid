package com.example.thebandbook.navigation


sealed class NavigationEvent {
    object NavigateBack: NavigationEvent()
    object NavigateToSignUp: NavigationEvent()
    object NavigateToCalendar: NavigationEvent()
    object NavigateToForum: NavigationEvent()
    data class NavigateToForumThreadScreen(val threadId: Int): NavigationEvent()
}
