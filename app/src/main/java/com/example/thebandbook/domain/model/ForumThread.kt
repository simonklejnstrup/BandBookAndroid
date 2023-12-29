package com.example.thebandbook.domain.model

import java.time.Instant
import java.time.LocalDateTime

data class ForumThread (
    val id: Int,
    var title: String,
    var createdBy: String,
    var createdAt: Instant,
    var comments: MutableList<Comment> = mutableListOf()
)

fun ForumThread.addComment(comment: Comment) {
    if (comment.threadId == this.id) {
        comments.add(comment)
    } else {
        println("Comment does not belong to this thread")
    }
}