package com.project.recipeapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailsDto(
    val id: String = "",
    val name: String = "",
    val shortDescription: String? = null,
    val publishedAt: String = "",
    val ingredients: List<IngredientDto> = emptyList(),
    val instructions: List<InstructionDto> = emptyList(),
    val liked: Boolean = false
)