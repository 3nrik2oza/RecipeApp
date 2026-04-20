package com.project.recipeapp.domain

import java.time.Instant

data class Recipe(
    val id: String,
    val name: String,
    val shortDescription: String?,
    val publishedAt: Instant,
    val liked: Boolean
)