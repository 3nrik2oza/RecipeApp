package com.project.recipeapp.presentation.recipe_list


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.recipeapp.presentation.asString
import com.project.recipeapp.presentation.components.ErrorView
import com.project.recipeapp.presentation.components.ProgressView
import com.project.recipeapp.presentation.recipe_list.components.RecipeList
import com.project.recipeapp.presentation.recipe_list.components.TabView
import com.project.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun RecipeListScreen(
    state: RecipeListScreenState,
    onAction: (RecipeListScreenAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        TabView(
            selectedTab = state.selectedTab,
            onTabSelected = {index -> onAction(RecipeListScreenAction.OnTabSelected(index))}
        )

        when{
            state.isLoadingInitial && state.recipeList.isEmpty() -> ProgressView()
            state.errorMessage != null && state.recipeList.isEmpty() -> ErrorView(
                errorMessage = state.errorMessage.asString(),
                onRetry = { onAction(RecipeListScreenAction.OnRetryLoading) },
                modifier = Modifier.fillMaxSize().padding(10.dp)
            )
            else -> RecipeList(
                recipes = state.recipeList,
                isLoadingMore = state.isLoadingMore,
                paginationErrorMessage = state.paginationErrorMessage,
                onLoadMore = { onAction(RecipeListScreenAction.OnLoadNextPage) },
                onOpenRecipe = { id -> onAction(RecipeListScreenAction.OnOpenRecipe(id))}
            )
        }

    }
}



@Preview
@Composable
private fun Preview() {
    RecipeAppTheme {
        RecipeListScreen(
            state = RecipeListScreenState(),
            onAction = {}
        )
    }
}