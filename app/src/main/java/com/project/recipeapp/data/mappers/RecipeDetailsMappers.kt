package com.project.recipeapp.data.mappers

import com.project.recipeapp.data.dto.IngredientDto
import com.project.recipeapp.data.dto.InstructionDto
import com.project.recipeapp.data.dto.RecipeDetailsDto
import com.project.recipeapp.domain.Ingredient
import com.project.recipeapp.domain.Instruction
import com.project.recipeapp.domain.RecipeDetails
import java.time.Instant

fun RecipeDetailsDto.toRecipeDetails(): RecipeDetails{
    return RecipeDetails(
        id = id,
        name = name,
        shortDescription = shortDescription,
        publishedAt = Instant.parse(publishedAt),
        ingredients = ingredients.map { it.toIngredient() },
        instructions = instructions.map { it.toInstruction() },
        liked = liked
    )
}

fun IngredientDto.toIngredient(): Ingredient{
    return Ingredient(
        name = name,
        amount= amount,
        unit = unit
    )
}

fun InstructionDto.toInstruction(): Instruction {
    return Instruction(
        step = step,
        description = description
    )
}