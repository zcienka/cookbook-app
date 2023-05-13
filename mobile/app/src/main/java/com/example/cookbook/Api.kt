package com.example.cookbook
import com.example.cookbook.models.Category
import com.example.cookbook.models.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/")
    fun getRecipes(): Call<List<Recipe>>
    @GET("/category")
    fun getCategories(): Call<List<Category>>
}