package com.example.thebandbook.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://your.api.url/") // Replace with your API base URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(EventApiService::class.java)
