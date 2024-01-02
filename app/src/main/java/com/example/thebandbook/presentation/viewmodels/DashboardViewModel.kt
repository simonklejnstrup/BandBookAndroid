package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.screens.common.BottomSheetState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel: ViewModel() {

    private val _bottomSheetState = MutableStateFlow<BottomSheetState>(BottomSheetState.Open)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    fun openDashboardBottomSheet() {
        _bottomSheetState.value = BottomSheetState.Open
    }

    fun closeBottomSheet() {
        _bottomSheetState.value = BottomSheetState.Closed
    }

    fun onLogout() {

    }
}