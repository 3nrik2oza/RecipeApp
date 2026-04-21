package com.project.recipeapp.data.mappers


import com.project.recipeapp.data.models.RecipeListItem
import com.project.recipeapp.domain.Recipe

fun RecipeListItem.toRecipe() : Recipe{
    return Recipe(
        id = id,
        name = name,
        shortDescription = shortDescription,
        publishedAt = publishedAt,
        liked = liked
    )
}
