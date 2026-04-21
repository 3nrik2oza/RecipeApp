package com.project.recipeapp.domain

import java.time.OffsetDateTime


data class RecipeDetails(
    val id: String = "",
    val name: String = "",
    val shortDescription: String? = null,
    val publishedAt: OffsetDateTime = OffsetDateTime.MIN,
    val ingredients: List<Ingredient> = emptyList(),
    val instructions: List<Instruction> = emptyList(),
    val liked: Boolean = false
)
