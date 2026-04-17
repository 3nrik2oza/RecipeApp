package com.project.recipeapp.presentation.recipe_details

import com.project.recipeapp.domain.RecipeDetails
import com.project.recipeapp.presentation.UiState
import com.project.recipeapp.presentation.UiText

data class RecipeDetailsState(
    val uiState: UiState = UiState.LOADING,
    val errorMessage: UiText? = null,
    val recipeId: String = "",
    val recipe: RecipeDetails = RecipeDetails(),
    val showErrorDialog: Boolean = false,
    val dialogErrorMessage: UiText? = null
)