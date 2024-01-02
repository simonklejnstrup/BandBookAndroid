package com.example.thebandbook.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebandbook.data.apiService
import com.example.thebandbook.domain.model.EventType
import com.example.thebandbook.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

open class CreateEventViewModel : ViewModel() {
    private val _eventData = MutableStateFlow(EventData())
    open val eventData = _eventData.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    suspend fun onBackPressed() {
        _navigationEvent.emit(NavigationEvent.NavigateBack)
    }
    fun setTitle(title: String) {
        _eventData.value = eventData.value.copy(title = title)
    }

    fun clearTitle() {
        _eventData.value = eventData.value.copy(title = "")
    }

    fun setAddress(address: String) {
        _eventData.value = eventData.value.copy(address = address)
    }

    fun setDate(date: LocalDate) {
        _eventData.value = eventData.value.copy(date = date)
    }

    fun setTimeOfGetIn(time: LocalTime) {
        _eventData.value = eventData.value.copy(timeOfGetIn = time)
    }

    fun setTimeOfSoundcheck(time: LocalTime) {
        _eventData.value = eventData.value.copy(timeOfSoundcheck = time)
    }

    fun setTimeOfConcert(time: LocalTime) {
        _eventData.value = eventData.value.copy(timeOfConcert = time)
    }

    fun setTimeOfDone(time: LocalTime) {
        _eventData.value = eventData.value.copy(timeOfDone = time)
    }

    fun clearAddress() {
        _eventData.value = eventData.value.copy(address = "")
    }

    fun setSalaryPerPerson(salary: Int) {
        _eventData.value = eventData.value.copy(salaryPerPerson = salary)
    }

    fun setCostOfRentalGear(cost: Int) {
        _eventData.value = eventData.value.copy(costOfRentalGear = cost)
    }

    fun setCostOfTransport(cost: Int) {
        _eventData.value = eventData.value.copy(costOfTransport = cost)
    }

    fun setExtraCosts(cost: Int) {
        _eventData.value = eventData.value.copy(extraCosts = cost)
    }

    fun setNameOfContactPerson(name: String) {
        _eventData.value = eventData.value.copy(nameOfContactPerson = name)
    }

    fun setTelephoneNumberOfContactPerson(phone: String) {
        _eventData.value = eventData.value.copy(telephoneNumberOfContactPerson = phone)
    }
    fun setNumberOfSets(numberOfSets: Int) {
        _eventData.value = eventData.value.copy(numberOfSets = numberOfSets)

    }

    fun setNote(note: String) {
        _eventData.value = eventData.value.copy(note = note)
    }

    fun setEventType(type: String) {
        _eventData.value = eventData.value.copy(type = type)
    }

    fun setSetLength(setLength: Int) {
        _eventData.value = eventData.value.copy(lengthOfEachSet = setLength)
    }


    // Function to submit the event data
    fun submitEvent() {
        println(eventData.value)
        viewModelScope.launch {
            try {
                val response = apiService.createEvent(eventData.value)
                if (response.isSuccessful) {
                    println("POST request Succesful")
                } else {
                    println("POST request failed")
                }
            } catch (e: Exception) {
                _navigationEvent.emit(NavigationEvent.NavigateBack)
                println("POST request network exception ${e.message}")
            }
        }
    }


    private val _selectedEventType = mutableStateOf(EventType.Gig)
    val selectedEventType: State<EventType> = _selectedEventType

    fun onEventTypeSelected(eventType: EventType) {
        _selectedEventType.value = eventType
        setEventType(eventType.displayName)
    }

    // Internal data class to hold the event data
    data class EventData(
        val title: String = "",
        val address: String = "",
        val date: LocalDate? = null,
        val timeOfGetIn: LocalTime = LocalTime.now(),
        val timeOfSoundcheck: LocalTime = LocalTime.now(),
        val timeOfConcert: LocalTime = LocalTime.now(),
        val timeOfDone: LocalTime = LocalTime.now(),
        val salaryPerPerson: Int = 0,
        val costOfRentalGear: Int = 0,
        val costOfTransport: Int = 0,
        val extraCosts: Int = 0,
        val nameOfContactPerson: String = "",
        val telephoneNumberOfContactPerson: String = "",
        val note: String = "",
        val type: String = "Gig",
        val lengthOfEachSet: Int = 45,
        val numberOfSets: Int = 2
    )
}

