package com.example.thebandbook.data.threaddata

import com.example.thebandbook.authentication.firebase.sign_in.FirebaseUserData


class ThreadRepository {
    private val threadApiService = MockThreadApiService()

    fun createThread(userInput: String, currentUser: FirebaseUserData) = threadApiService.createThread(content = userInput, currentUser = currentUser)

    fun getThreadById(threadId: Int) = threadApiService.getThreadById(threadId)

}