package com.example.thebandbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebandbook.authentication.firebase.sign_in.FirebaseUserData
import com.example.thebandbook.data.commentdata.CommentRepository
import com.example.thebandbook.data.commentdata.commentApiService
import com.example.thebandbook.data.threaddata.ThreadRepository
import com.example.thebandbook.data.threaddata.mockThreads
import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.domain.model.ForumThread
import com.example.thebandbook.navigation.NavigationEvent
import com.example.thebandbook.presentation.bottomsheets.BottomSheetState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.random.Random

class ForumThreadViewModel: ViewModel() {
    private val _bottomSheetState = MutableStateFlow<BottomSheetState>(BottomSheetState.Closed)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _selectedThread = MutableStateFlow<ForumThread?>(null)
    val selectedThread = _selectedThread.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    // State for tracking if the comment was successfully submitted
    private val _commentSubmissionSuccessful = MutableStateFlow(false)
    val commentSubmissionSuccessful = _commentSubmissionSuccessful.asSharedFlow()

    private val threadRepository = ThreadRepository()
    private val commentRepository = CommentRepository()



    fun openBottomSheetWithThread(thread: ForumThread) {
        _selectedThread.value = thread
        _bottomSheetState.value = BottomSheetState.Open
    }

    fun setCommentSubmissionSuccessful(success: Boolean) {
        _commentSubmissionSuccessful.value = success
        println("_commentSubmissionSuccessful.value ${_commentSubmissionSuccessful.value}")
    }

    fun loadThread(threadId: Int) {
        viewModelScope.launch {
            val response = threadRepository.getThreadById(threadId)
            if (response.isSuccessful) {
                // Assign the thread from the response to _selectedThread
                _selectedThread.value = response.body()
                println("xxxselectedThread.value ${selectedThread.value}")
                println("_selectedThread.valueresponse.body() ${response.body()}")
            } else {
                // Handle the error case
                // _selectedThread.value = null // if you want to reset the thread in case of an error
            }
        }
    }

    fun navigateToForumThreadScreen(thread: ForumThread) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToForumThreadScreen(thread.id))
        }
    }

    fun closeBottomSheet() {
        _selectedThread.value = null
        _bottomSheetState.value = BottomSheetState.Closed
    }

    fun submitComment(userInput: String, thread: ForumThread, currentUser: FirebaseUserData) {
        // Find the thread in the mockThreads list
        val foundThread = mockThreads.find { it.id == thread.id }

        // Create the new comment
        val newComment = currentUser.name?.let {name ->
            Comment(
                id = Random.nextInt(),
                content = userInput,
                createdBy = name,
                createdAt = Instant.now(),
                threadId = thread.id
            )
        }
//        newComment.let {
//            // Update the list of comments
//            val updatedComments = thread.comments + newComment!!
//            val updatedCommentsMutableList = updatedComments.toMutableList()
//
//            // Update the thread with the new list of comments
//            val updatedThread = thread.copy(comments = updatedCommentsMutableList)
//            _selectedThread.value = updatedThread
//        }

        viewModelScope.launch {
            try {
                val response = commentRepository.createComment(
                    userInput = userInput,
                    threadId = thread.id,
                    currentUser = currentUser
                )
                if (response.isSuccessful) {
                    println("POST request Succesful")

//                    // Update the list of comments
//                    val updatedComments = thread.comments + newComment!!
//                    val updatedCommentsMutableList = updatedComments.toMutableList()
//
//                    // Update the thread with the new list of comments
//                    val updatedThread = thread.copy(comments = updatedCommentsMutableList)
//                    _selectedThread.value = updatedThread

                    loadThread(thread.id)

                    // Clears input textfield
                    _commentSubmissionSuccessful.value = true

//                    _navigationEvent.emit(NavigationEvent.NavigateToForum)
                } else {
                    println("POST request failed")

                }
            } catch (e: Exception) {
                _navigationEvent.emit(NavigationEvent.NavigateBack)
                println("POST request network exception ${e.message}")
            }
        }
    }
}