package com.example.thebandbook.data

import com.example.thebandbook.presentation.screens.Event
import java.time.LocalDate
import java.time.LocalTime

val mockEvents = listOf(
    Event(
        id = 1,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2023, 8, 15), // YYYY, MM, DD
        timeOfGetIn = LocalTime.of(9, 0), // HH, MM
        timeOfSoundcheck = LocalTime.of(11, 0),
        timeOfConcert = LocalTime.of(16, 0),
        timeOfDone = LocalTime.of(23, 0),
        salaryPerPerson = 250,
        costOfRentalGear = 600,
        costOfTransport = 200,
        extraCosts = 150,
        nameOfContactPerson = "Jane Smith",
        telephoneNumberOfContactPerson = "987-654-3210",
        note = "Indoor event, air-conditioned."
    ),
    Event(
        id = 2,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2023, 8, 15), // YYYY, MM, DD
        timeOfGetIn = LocalTime.of(9, 0), // HH, MM
        timeOfSoundcheck = LocalTime.of(11, 0),
        timeOfConcert = LocalTime.of(16, 0),
        timeOfDone = LocalTime.of(23, 0),
        salaryPerPerson = 250,
        costOfRentalGear = 600,
        costOfTransport = 200,
        extraCosts = 150,
        nameOfContactPerson = "Jane Smith",
        telephoneNumberOfContactPerson = "987-654-3210",
        note = "Indoor event, air-conditioned."
    ),
    Event(
        id = 3,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2024, 8, 15), // YYYY, MM, DD
        timeOfGetIn = LocalTime.of(9, 0), // HH, MM
        timeOfSoundcheck = LocalTime.of(11, 0),
        timeOfConcert = LocalTime.of(16, 0),
        timeOfDone = LocalTime.of(23, 0),
        salaryPerPerson = 250,
        costOfRentalGear = 600,
        costOfTransport = 200,
        extraCosts = 150,
        nameOfContactPerson = "Jane Smith",
        telephoneNumberOfContactPerson = "987-654-3210",
        note = "Indoor event, air-conditioned."
    ),
    Event(
        id = 4,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2024, 8, 15), // YYYY, MM, DD
        timeOfGetIn = LocalTime.of(9, 0), // HH, MM
        timeOfSoundcheck = LocalTime.of(11, 0),
        timeOfConcert = LocalTime.of(16, 0),
        timeOfDone = LocalTime.of(23, 0),
        salaryPerPerson = 250,
        costOfRentalGear = 600,
        costOfTransport = 200,
        extraCosts = 150,
        nameOfContactPerson = "Jane Smith",
        telephoneNumberOfContactPerson = "987-654-3210",
        note = "Indoor event, air-conditioned."
    )
)