package com.example.cookbookapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}