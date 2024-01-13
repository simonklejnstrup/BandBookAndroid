package com.example.thebandbook.data.threaddata

import com.example.thebandbook.domain.model.Comment
import com.example.thebandbook.domain.model.ForumThread
import java.time.Instant
import java.time.temporal.ChronoUnit

fun randomPastInstant(daysAgo: Long): Instant {
    return Instant.now().minus(daysAgo, ChronoUnit.DAYS)
}

val mockThreads = mutableListOf(
    ForumThread(
        id = 1,
        title = "How to Learn Kotlin Effectively",
        createdBy = "Simon Klejnstrup",
        createdAt = randomPastInstant(30), // 30 days ago
        comments = mutableListOf(
            Comment(
                id = 1,
                content = "Great post! I've found online courses to be very helpful.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(28), // 28 days ago
                threadId = 1
            ),
            Comment(
                id = 2,
                content = "Don't forget to practice by building small projects.",
                createdBy = "Simon Klejnstrup",
                createdAt = randomPastInstant(27), // 27 days ago
                threadId = 1
            ),
            Comment(
                id = 3,
                content = "Is there a Kotlin community or forum where I can ask questions?",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(25), // 25 days ago
                threadId = 1
            ),
            Comment(
                id = 4,
                content = "I recommend checking out the Kotlin subreddit and Stack Overflow.",
                createdBy = "Simon Klejnstrup",
                createdAt = randomPastInstant(23), // 23 days ago
                threadId = 1
            ),
            Comment(
                id = 5,
                content = "Also, reading Kotlin in Action really helped me understand the basics.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(20), // 20 days ago
                threadId = 1
            ),
            Comment(
                id = 6,
                content = "Thanks for all the suggestions, everyone!",
                createdBy = "Simon Klejnstrup",
                createdAt = randomPastInstant(18), // 18 days ago
                threadId = 1
            )
        )
    ),
    ForumThread(
        id = 2,
        title = "Best Practices for Android Development",
        createdBy = "Henriette Lund",
        createdAt = randomPastInstant(40), // 40 days ago
        comments = mutableListOf(
            Comment(
                id = 7,
                content = "Always keep performance in mind!",
                createdBy = "Simon Klejnstrup",
                createdAt = randomPastInstant(38), // 38 days ago
                threadId = 2
            ),
            Comment(
                id = 8,
                content = "I recommend following the official Android coding standards.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(35), // 35 days ago
                threadId = 2
            ),
            Comment(
                id = 9,
                content = "Using the latest Android Studio helps a lot with linting and best practices.",
                createdBy = "Simon Klejnstrup",
                createdAt = randomPastInstant(33), // 33 days ago
                threadId = 2
            ),
            Comment(
                id = 10,
                content = "Does anyone have tips for optimizing RecyclerView?",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(30), // 30 days ago
                threadId = 2
            )
            // ... additional comments ...
        )
    ),
    ForumThread(
        id = 3,
        title = "Navigating the Challenges of Remote Work",
        createdBy = "Henriette Lund",
        createdAt = randomPastInstant(50), // 50 days ago
        comments = mutableListOf(
            Comment(
                id = 11,
                content = "Staying disciplined and maintaining a work-life balance can be tough.",
                createdBy = "Simon Klejnstrup",
                createdAt = randomPastInstant(48), // 48 days ago
                threadId = 3
            ),
            Comment(
                id = 12,
                content = "I found that regular virtual meetups with my team help keep us connected.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(47), // 47 days ago
                threadId = 3
            )
            // ... additional comments ...
        )
    ),
    ForumThread(
        id = 4,
        title = "Advancements in AI and Machine Learning",
        createdBy = "Simon Klejnstrup",
        createdAt = randomPastInstant(60), // 60 days ago
        comments = mutableListOf(
            Comment(
                id = 13,
                content = "AI is rapidly evolving, but there's still a long way to go in terms of ethics.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(59), // 59 days ago
                threadId = 4
            )
            ,Comment(
                id = 14,
                content = "AI is rapidly evolving, but there's still a long way to go in terms of ethics.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(59), // 59 days ago
                threadId = 4
            )
            ,Comment(
                id = 15,
                content = "AI is rapidly evolving, but there's still a long way to go in terms of ethics.",
                createdBy = "Henriette Lund",
                createdAt = randomPastInstant(59), // 59 days ago
                threadId = 4
            )
        )
    )
)
