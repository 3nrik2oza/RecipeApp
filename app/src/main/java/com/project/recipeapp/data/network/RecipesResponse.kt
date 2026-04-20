package com.project.recipeapp.data.network

import com.project.recipeapp.data.dto.RecipeDto
import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val content: List<RecipeDto>
)