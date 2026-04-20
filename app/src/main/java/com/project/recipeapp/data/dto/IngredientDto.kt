package com.project.recipeapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val name: String,
    val amount: String,
    val unit: String
)
