package com.example.thebandbook.data

import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.domain.model.ForumThread
import java.time.Instant


val mockThreads = listOf(
    ForumThread(
        id = 1,
        title = "ex title",
        createdBy = "Simon Klejnstrup",
        createdAt = Instant.now(),
        comments = mutableListOf(
            Comment(
                id = 1,
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud ",
                createdBy = "Henriette Lund",
                createdAt = Instant.now(),
                threadId = 1
            ),
            Comment(
                id = 2,
                content = "Lang kommentar",
                createdBy = "Henriette Lund",
                createdAt = Instant.now(),
                threadId = 1
            ),

        )
    ),
    ForumThread(
        id = 2,
        title = "ex title",
        createdBy = "Simon Klejnstrup",
        createdAt = Instant.now(),
        comments = mutableListOf(
            Comment(
                id = 3,
                content = "Lang kommentar",
                createdBy = "Henriette Lund",
                createdAt = Instant.now(),
                threadId = 1
            ),
            Comment(
                id = 4,
                content = "Lang kommentar",
                createdBy = "Henriette Lund",
                createdAt = Instant.now(),
                threadId = 1
            ),

        )
    ),

)