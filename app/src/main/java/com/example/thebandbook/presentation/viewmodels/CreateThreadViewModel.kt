package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebandbook.authentication.GoogleAuthClientSingleton
import com.example.thebandbook.data.threaddata.ThreadRepository
import com.example.thebandbook.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CreateThreadViewModel: ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val currentUser = GoogleAuthClientSingleton.googleAuthUiClient.getSignedInUser()

    private val repository = ThreadRepository()

    fun submitPost(userInput: String) {
        viewModelScope.launch {
            try {
                val response = currentUser?.let { repository.createThread(userInput, it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        println("POST request Succesful")
                        _navigationEvent.emit(NavigationEvent.NavigateToForum)
                    } else {
                        println("POST request failed")

                    }
                }
            } catch (e: Exception) {
                _navigationEvent.emit(NavigationEvent.NavigateBack)
                println("POST request network exception ${e.message}")
            }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateBack)
        }
    }
}