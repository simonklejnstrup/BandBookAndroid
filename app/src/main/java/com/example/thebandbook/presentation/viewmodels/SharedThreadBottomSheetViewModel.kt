package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.thebandbook.domain.model.ForumThread
import com.example.thebandbook.presentation.screens.common.BottomSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedThreadBottomSheetViewModel: ViewModel() {
    private val _bottomSheetState = MutableStateFlow<BottomSheetState>(BottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _selectedThread = MutableStateFlow<ForumThread?>(null)
    val selectedThread = _selectedThread.asStateFlow()

    fun openBottomSheetWithThread(thread: ForumThread) {
        _selectedThread.value = thread
        _bottomSheetState.value = BottomSheetState.Open
    }

    fun closeBottomSheet() {
        _selectedThread.value = null
        _bottomSheetState.value = BottomSheetState.Closed
    }
}