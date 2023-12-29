package com.example.thebandbook.domain.model

import java.time.Instant

data class User (
    val id: Int,
    var firstname: String,
    var lastname: String,
    var email: String,
    var band: String,
    var created: Instant = Instant.now()
    )
