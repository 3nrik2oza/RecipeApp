package com.project.recipeapp.presentation.recipe_list

import com.project.recipeapp.domain.Recipe
import com.project.recipeapp.presentation.UiText
import com.project.recipeapp.presentation.recipe_list.components.TabState

data class RecipeListScreenState(
    val isLoadingInitial: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: UiText? = null,
    val paginationErrorMessage: UiText? = null,
    val recipeList: List<Recipe> = emptyList(),
    val selectedTab: TabState = TabState.HOME,
    val currentPage: Int = 1,
)