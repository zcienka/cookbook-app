package com.example.cookbookapp
import retrofit2.Call
import retrofit2.http.GET

interface Api {
        @GET("http://10.0.2.2:8000")
    fun getRecipes(): Call<List<Recipe>>
}