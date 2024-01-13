package com.example.thebandbook.data.threaddata

import com.example.thebandbook.authentication.firebase.sign_in.FirebaseUserData
import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.domain.model.ForumThread
import retrofit2.Response
import java.time.Instant
import kotlin.random.Random

class MockThreadApiService {

    fun createThread(content: String, currentUser: FirebaseUserData?): Response<ForumThread> {
        val threadId = Random.nextInt()
        val newThread = currentUser?.name?.let {
            ForumThread(
                id = threadId,
                title = "",
                createdBy = currentUser.name,
                createdAt = Instant.now(),
                comments = mutableListOf(
                    Comment(
                        id = Random.nextInt(),
                        content = content,
                        createdBy = currentUser.name,
                        createdAt = Instant.now(),
                        threadId = threadId
                        )
                )
            )
        }
        if (newThread != null) {
            mockThreads.add(newThread)
        }

        // Simulate a successful response
        return Response.success(newThread)
    }
}