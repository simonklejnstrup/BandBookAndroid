package com.example.thebandbook.data.eventdata

import com.example.thebandbook.presentation.viewmodels.CreateEventViewModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApiService {
    @POST("http://192.168.39.1:8080/event")
    suspend fun createEvent(@Body eventData: CreateEventViewModel.EventData): Response<EventResponse>
}
