package com.project.recipeapp.data.mappers


import com.project.recipeapp.data.models.RecipeDetail
import com.project.recipeapp.domain.Ingredient
import com.project.recipeapp.domain.Instruction
import com.project.recipeapp.domain.RecipeDetails

fun RecipeDetail.toRecipeDetails(): RecipeDetails{
    return RecipeDetails(
        id = id,
        name = name,
        shortDescription = shortDescription,
        publishedAt = publishedAt,
        ingredients = ingredients.map { it.toIngredient() },
        instructions = instructions.map { it.toInstruction() },
        liked = liked
    )
}

fun com.project.recipeapp.data.models.Ingredient.toIngredient(): Ingredient{
    return Ingredient(
        name = name,
        amount= amount,
        unit = unit
    )
}

fun com.project.recipeapp.data.models.Instruction.toInstruction(): Instruction {
    return Instruction(
        step = step,
        description = description
    )
}