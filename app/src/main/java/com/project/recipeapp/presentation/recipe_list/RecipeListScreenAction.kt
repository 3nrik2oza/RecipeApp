package com.project.recipeapp.presentation.recipe_list

import com.project.recipeapp.presentation.recipe_list.components.TabState

sealed interface RecipeListScreenAction {

    data class OnOpenRecipe(val recipeId: String): RecipeListScreenAction
    data class OnTabSelected(val tabSelected: TabState): RecipeListScreenAction
    data object OnLoadNextPage: RecipeListScreenAction
    data object OnRetryLoading: RecipeListScreenAction
}