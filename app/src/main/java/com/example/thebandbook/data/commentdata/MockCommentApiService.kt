package com.example.thebandbook.data.commentdata

import com.example.thebandbook.authentication.firebase.sign_in.FirebaseUserData
import com.example.thebandbook.data.threaddata.mockThreads
import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.domain.model.ForumThread
import retrofit2.Response
import java.time.Instant
import kotlin.random.Random

class MockCommentApiService {
    fun createComment(
        content: String,
        threadId: Int,
        currentUser: FirebaseUserData?
    ): Response<ForumThread> {
        // Find the thread in the mockThreads list
        val foundThread = mockThreads.find { it.id == threadId }

        // Create the new comment
        val newComment = currentUser?.name?.let {
            Comment(
                id = Random.nextInt(),
                content = content,
                createdBy = currentUser.name,
                createdAt = Instant.now(),
                threadId = threadId
            )
        }

        // Add the new comment to the found thread
        newComment?.let {
            foundThread?.comments?.add(it)
        }

        // Return the updated thread as a response
        return Response.success(foundThread)
    }
}