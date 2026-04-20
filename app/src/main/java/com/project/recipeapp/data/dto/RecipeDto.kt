package com.project.recipeapp.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class RecipeDto(
    val id: String,
    val name: String,
    val shortDescription: String? = null,
    val publishedAt: String,
    val liked: Boolean
)
