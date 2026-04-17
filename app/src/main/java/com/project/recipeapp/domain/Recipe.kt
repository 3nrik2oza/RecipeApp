package com.project.recipeapp.domain

data class Recipe(
    val id: String,
    val name: String,
    val shortDescription: String?,
    val publishedAt: String, // maybe change to date type
    val liked: Boolean
)