package com.project.recipeapp.presentation.recipe_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.project.recipeapp.Route
import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.domain.onError
import com.project.recipeapp.domain.onSuccess
import com.project.recipeapp.presentation.toErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = MutableStateFlow(RecipeDetailsState())
    val state = _state.asStateFlow()

    init {
        val route = savedStateHandle.toRoute<Route.DetailScreen>()
        val recipeId = route.recipeId

        _state.update { it.copy(recipeId = recipeId) }
        loadRecipeDetails(recipeId)
    }

    fun onAction(action: RecipeDetailsAction) {
        when (action) {
            is RecipeDetailsAction.OnRetryLoading -> {
                _state.update { it.copy(isLoadingInitial = true, errorMessage = null) }
                loadRecipeDetails(_state.value.recipeId)
            }
            is RecipeDetailsAction.OnLikeToggle -> {
                viewModelScope.launch {
                    repository.recipeLikeToggle(_state.value.recipeId)
                        .onError { error ->
                            _state.update { it.copy(showErrorDialog = true, dialogErrorMessage = error.toErrorMessage()) }
                        }
                        .onSuccess { recipeUpdated ->
                            _state.update { it.copy(recipe = recipeUpdated) }
                        }
                }
            }
            is RecipeDetailsAction.OnCloseMessageDialog -> {
                _state.update { it.copy(showErrorDialog = false, dialogErrorMessage = null) }
            }
            else -> {}
        }
    }

    private fun loadRecipeDetails(recipeId: String){
        viewModelScope.launch {
            repository.getRecipe(recipeId)
                .onError { error ->
                    _state.update { it.copy(isLoadingInitial = false, errorMessage = error.toErrorMessage()) }
                }
                .onSuccess { recipe ->
                    _state.update { it.copy(isLoadingInitial = false, recipe = recipe) }
                }
        }
    }

}