package com.example.recipeapp

data class Recipe(
    val id: String,
    val calories: Int,
    val description: String,
    val ingredients: String,
    val preparation_time: String,
    val title: String
)