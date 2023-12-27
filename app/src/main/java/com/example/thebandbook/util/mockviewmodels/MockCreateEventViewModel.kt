package com.example.thebandbook.util.mockviewmodels

import androidx.compose.runtime.mutableStateOf
import com.example.thebandbook.presentation.viewmodels.CreateEventViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

class MockCreateEventViewModel : CreateEventViewModel() {
    // Override functions and provide test data
    override val eventData = MutableStateFlow(EventData())

    // Implement any necessary functions for the preview
    // For example:


    // ... other necessary overrides and mock implementations
}

// If CreateEventViewModel is an interface or has abstract methods, make sure to implement them
