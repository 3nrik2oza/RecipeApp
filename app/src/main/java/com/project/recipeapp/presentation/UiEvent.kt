package com.project.recipeapp.presentation

sealed interface UiEvent {
    data class ShowSnackbar(val message: UiText) : UiEvent
}