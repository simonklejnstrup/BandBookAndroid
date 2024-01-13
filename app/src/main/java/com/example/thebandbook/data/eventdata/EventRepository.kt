package com.example.thebandbook.data.eventdata

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://my.api.url/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val eventApiService = MockEventApiService() // retrofit.create(EventApiService::class.java)

