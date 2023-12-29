package com.example.thebandbook.domain.model

import java.time.Instant

data class Comment (
    val id: Int,
    var content: String,
    var createdBy: String,
    var createdAt: Instant,
    var threadId: Int
)