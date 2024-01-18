package com.example.thebandbook.data.commentdata

import com.example.thebandbook.authentication.firebase.sign_in.FirebaseUserData

val commentApiService = MockCommentApiService()

class CommentRepository {
    private val commentApiService = MockCommentApiService()

    fun createComment(userInput: String, threadId: Int, currentUser: FirebaseUserData) = commentApiService.createComment(content = userInput, threadId = threadId, currentUser = currentUser)
}