package com.project.recipeapp.presentation.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.domain.onError
import com.project.recipeapp.domain.onSuccess
import com.project.recipeapp.presentation.UiState
import com.project.recipeapp.presentation.toErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val repository: RecipeRepository
) : ViewModel() {


    private val _state = MutableStateFlow(RecipeDetailsState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RecipeDetailsState()
        )

    fun onAction(action: RecipeDetailsAction) {
        when (action) {
            is RecipeDetailsAction.OnIdSelected -> {
                _state.update { it.copy(recipeId = action.recipeId) }
                loadRecipeDetails(action.recipeId)
            }
            is RecipeDetailsAction.OnRetryLoading -> {
                _state.update { it.copy(uiState = UiState.LOADING, errorMessage = null) }
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
                    _state.update { it.copy(uiState = UiState.ERROR, errorMessage = error.toErrorMessage()) }
                }
                .onSuccess { recipe ->
                    _state.update { it.copy(uiState = UiState.SUCCESS, recipe = recipe) }
                }
        }
    }

}