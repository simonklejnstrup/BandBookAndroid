package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.thebandbook.domain.model.Event
import com.example.thebandbook.presentation.bottomsheets.BottomSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedEventDetailsBottomSheetViewModel : ViewModel() {
    private val _bottomSheetState = MutableStateFlow<BottomSheetState>(BottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()


    fun openBottomSheetWithEvent(event: Event) {
        _selectedEvent.value = event
        _bottomSheetState.value = BottomSheetState.Open
    }

    fun closeBottomSheet() {
        _selectedEvent.value = null
        _bottomSheetState.value = BottomSheetState.Closed
    }


    fun setSelectedEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun clearSelectedEvent() {
        _selectedEvent.value = null
    }

}
