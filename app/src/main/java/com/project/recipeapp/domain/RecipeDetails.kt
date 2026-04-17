package com.project.recipeapp.domain

data class RecipeDetails(
    val id: String = "",
    val name: String = "",
    val shortDescription: String? = null,
    val publishedAt: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val instructions: List<Instruction> = emptyList(),
    val liked: Boolean = false
)
