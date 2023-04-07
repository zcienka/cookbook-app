package com.example.cookbook
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/")
    fun getRecipes(): Call<List<Recipe>>
}