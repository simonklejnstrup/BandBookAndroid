package com.example.thebandbook.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Int,
    val title: String,
    val address: String,
    val date: LocalDate,
    val timeOfGetIn: LocalTime,
    val timeOfSoundcheck: LocalTime,
    val timeOfConcert: LocalTime,
    val timeOfDone: LocalTime,
    val salaryPerPerson: Int,
    val costOfRentalGear: Int,
    val costOfTransport: Int,
    val extraCosts: Int,
    val nameOfContactPerson: String,
    val telephoneNumberOfContactPerson: String,
    val note: String,
    val type: String,
    val lengthOfEachSet: Int = 45,
    val numberOfSets: Int = 2
)

enum class EventType(val displayName: String) {
    Gig("Gig"),
    Meeting("Meeting"),
    Rehearsal("Rehearsal");

    companion object {
        fun fromString(type: String): EventType {
            return when (type.lowercase()) {
                "gig" -> Gig
                "meeting" -> Meeting
                "rehearsal" -> Rehearsal
                else -> throw IllegalArgumentException("Unknown event type: $type")
            }
        }
    }

    fun getEventDescription(): String {
        return "Event Type: $displayName"
    }
}
