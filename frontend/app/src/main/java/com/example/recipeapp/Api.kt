package com.example.recipeapp
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("http://10.0.2.2:8000")
    suspend fun getRecipes(): Response<List<Recipe>>
}