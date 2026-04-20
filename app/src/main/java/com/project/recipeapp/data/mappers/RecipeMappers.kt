package com.project.recipeapp.data.mappers

import com.project.recipeapp.data.dto.RecipeDto
import com.project.recipeapp.domain.Recipe
import java.time.Instant

fun RecipeDto.toRecipe() : Recipe{
    return Recipe(
        id = id,
        name = name,
        shortDescription = shortDescription,
        publishedAt = Instant.parse(publishedAt),
        liked = liked
    )
}
