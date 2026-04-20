package com.project.recipeapp.presentation.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.domain.onError
import com.project.recipeapp.domain.onSuccess
import com.project.recipeapp.presentation.UiEvent
import com.project.recipeapp.presentation.recipe_list.components.TabState
import com.project.recipeapp.presentation.toErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListScreenViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _state = MutableStateFlow(RecipeListScreenState())
    val state = _state.asStateFlow()



    init{
        loadPage(1, false)
    }

    fun onAction(action: RecipeListScreenAction) {
        when (action) {
            is RecipeListScreenAction.OnTabSelected -> {
                if(_state.value.selectedTab == action.tabSelected){
                    return
                }

                _state.update { it.copy(selectedTab = action.tabSelected, currentPage = 1, isLoadingInitial = true) }
                loadPage(1, action.tabSelected == TabState.LIKED)
            }
            is RecipeListScreenAction.OnLoadNextPage -> {
                val current = _state.value
                if(!current.isLoadingMore){
                    loadPage(current.currentPage + 1, current.selectedTab == TabState.LIKED)
                }
            }
            is RecipeListScreenAction.OnRetryLoading -> {
                _state.update { it.copy(
                    errorMessage = null,
                    isLoadingInitial = true
                ) }
                loadPage(1, _state.value.selectedTab == TabState.LIKED)
            }
            else -> {}
        }
    }

    private fun loadPage(page: Int, likedOnly: Boolean){
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true, paginationErrorMessage = null) }

            recipeRepository.getRecipes(page, likedOnly).onError { error ->
                if(page == 1){
                    _state.update {
                        it.copy(
                            isLoadingInitial = false,
                            isLoadingMore = false,
                            errorMessage = error.toErrorMessage()
                        )
                    }
                }else{
                    _state.update { it.copy(
                        isLoadingMore = false, paginationErrorMessage = error.toErrorMessage(),
                    ) }
                    _eventFlow.send(UiEvent.ShowSnackbar(error.toErrorMessage()))
                }

            }.onSuccess { recipes ->
                _state.update {
                    it.copy(
                        isLoadingInitial = false,
                        recipeList = if (page== 1) recipes else it.recipeList + recipes,
                        currentPage = page,
                        isLoadingMore = false,
                    )
                }
            }
        }
    }

}