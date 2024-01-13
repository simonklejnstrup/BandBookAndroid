package com.example.thebandbook.data.eventdata

import com.example.thebandbook.domain.model.Event
import com.example.thebandbook.presentation.viewmodels.CreateEventViewModel
import kotlin.random.Random
import retrofit2.Response

class MockEventApiService {
    fun createEvent(event: CreateEventViewModel.EventData): Response<Event> {
        val newEvent = Event(
            id = Random.nextInt(),
            title = event.title,
            address = event.address,
            date = event.date,
            timeOfGetIn = event.timeOfGetIn,
            timeOfSoundcheck = event.timeOfSoundcheck,
            timeOfConcert = event.timeOfConcert,
            timeOfDone = event.timeOfDone,
            salaryPerPerson = event.salaryPerPerson,
            costOfRentalGear = event.costOfRentalGear,
            costOfTransport = event.costOfTransport,
            extraCosts = event.extraCosts,
            nameOfContactPerson = event.nameOfContactPerson,
            telephoneNumberOfContactPerson = event.telephoneNumberOfContactPerson,
            note = event.note,
            type = event.type,
            lengthOfEachSet = event.lengthOfEachSet,
            numberOfSets = event.numberOfSets)
        mockEvents.add(newEvent)

        // Simulate a successful response
        return Response.success(newEvent)
    }
}