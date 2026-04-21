package com.project.recipeapp.domain

import java.time.OffsetDateTime

data class Recipe(
    val id: String,
    val name: String,
    val shortDescription: String?,
    val publishedAt: OffsetDateTime,
    val liked: Boolean
)