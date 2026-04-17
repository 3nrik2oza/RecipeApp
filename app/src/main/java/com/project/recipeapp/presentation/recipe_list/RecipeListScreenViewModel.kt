package com.project.recipeapp.presentation.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.domain.onError
import com.project.recipeapp.domain.onSuccess
import com.project.recipeapp.presentation.UiState
import com.project.recipeapp.presentation.recipe_list.components.TabState
import com.project.recipeapp.presentation.toErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListScreenViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RecipeListScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadPage(1, false)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RecipeListScreenState()
        )


    fun onAction(action: RecipeListScreenAction) {
        when (action) {
            is RecipeListScreenAction.OnTabSelected -> {
                if(_state.value.selectedTab == action.tabSelected){
                    return
                }

                _state.update { it.copy(selectedTab = action.tabSelected, currentPage = 1, uiState = UiState.LOADING) }
                loadPage(1, action.tabSelected == TabState.LIKED)
            }
            is RecipeListScreenAction.OnLoadNextPage -> {
                val current = _state.value
                if(!current.isLoadingMore && current.uiState == UiState.SUCCESS){
                    loadPage(current.currentPage + 1, current.selectedTab == TabState.LIKED)
                }
            }
            is RecipeListScreenAction.OnRetryLoading -> {
                _state.update { it.copy(
                    uiState = UiState.LOADING,
                    errorMessage = null
                ) }
                loadPage(1, _state.value.selectedTab == TabState.LIKED)
            }
            else -> {}
        }
    }

    private fun loadPage(page: Int, likedOnly: Boolean){
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }

            recipeRepository.getRecipes(page, likedOnly).onError { error ->
                _state.update {
                    it.copy(
                        uiState = UiState.ERROR,
                        isLoadingMore = false,
                        errorMessage = error.toErrorMessage())
                }

            }.onSuccess { recipes ->
                _state.update {
                    it.copy(
                        uiState = UiState.SUCCESS,
                        recipeList = if (page== 1) recipes else it.recipeList + recipes,
                        currentPage = page,
                        isLoadingMore = false,
                    )
                }
            }
        }
    }

}