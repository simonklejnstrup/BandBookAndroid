package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.thebandbook.presentation.screens.common.BottomSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel: ViewModel() {
    private val _bottomSheetState = MutableStateFlow<BottomSheetState>(BottomSheetState.Open)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    fun openDashboardBottomSheet() {
        _bottomSheetState.value = BottomSheetState.Open
    }

    fun closeBottomSheet() {
        _bottomSheetState.value = BottomSheetState.Closed
    }

    fun onLogout() {

    }
}