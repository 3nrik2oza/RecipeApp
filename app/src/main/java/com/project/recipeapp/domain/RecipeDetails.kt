package com.project.recipeapp.domain

import java.time.Instant


data class RecipeDetails(
    val id: String = "",
    val name: String = "",
    val shortDescription: String? = null,
    val publishedAt: Instant = Instant.MIN,
    val ingredients: List<Ingredient> = emptyList(),
    val instructions: List<Instruction> = emptyList(),
    val liked: Boolean = false
)
