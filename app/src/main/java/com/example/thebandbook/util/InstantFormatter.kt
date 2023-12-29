package com.example.thebandbook.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatInstantToHoursAndMinutes(instant: Instant): String {
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    return zonedDateTime.format(formatter)
}
fun formatInstantToDateAndMonth(instant: Instant): String {
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("dd MMM")

    return zonedDateTime.format(formatter)
}
