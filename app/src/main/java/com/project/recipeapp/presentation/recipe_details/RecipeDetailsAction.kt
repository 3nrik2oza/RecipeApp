package com.project.recipeapp.presentation.recipe_details

sealed interface RecipeDetailsAction {

    data class OnIdSelected(val recipeId: String): RecipeDetailsAction

    data object OnLikeToggle: RecipeDetailsAction

    data object OnRetryLoading: RecipeDetailsAction

    data object OnBackPress: RecipeDetailsAction
    data object OnCloseMessageDialog: RecipeDetailsAction
}