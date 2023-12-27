package com.example.thebandbook.data

import com.example.thebandbook.presentation.viewmodels.CreateEventViewModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApiService {
    @POST("events/create")
    suspend fun createEvent(@Body eventData: CreateEventViewModel.EventData): Response<Unit> // Replace Unit with your response type
}
