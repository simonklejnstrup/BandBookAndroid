package com.example.thebandbook.data.eventdata

import com.example.thebandbook.domain.model.Event
import java.time.LocalDate
import java.time.LocalTime

val mockEvents = mutableListOf(
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
        note = "Indoor event, air-conditioned.",
        type = "meeting"
    ),
    Event(
        id = 2,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2023, 12, 12), // YYYY, MM, DD
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
        note = "Indoor event, air-conditioned.",
        type = "gig"
    ),
    Event(
        id = 3,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2024, 3, 16), // YYYY, MM, DD
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
        note = "Indoor event, air-conditioned.",
        type = "gig"
    ),
    Event(
        id = 4,
        title = "Mock Music Festival",
        address = "456 Festival Road, Harmony Town, HT 12345",
        date = LocalDate.of(2024, 1, 1), // YYYY, MM, DD
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
        note = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "Why do we use it?\n" +
                "\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "Where does it come from?\n" +
                "\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.",
        type = "rehearsal"
    )
)