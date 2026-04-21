package com.project.recipeapp.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.project.recipeapp.presentation.UiEvent
import com.project.recipeapp.presentation.asString
import com.project.recipeapp.presentation.recipe_list.RecipeListScreen
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenAction
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenViewModel
import org.koin.androidx.compose.koinViewModel

class ListScreenNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget>,
    private val snackbarHostState: SnackbarHostState
) : Node(buildContext){
    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = koinViewModel<RecipeListScreenViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val context = LocalContext.current
        LaunchedEffect(viewModel.eventFlow) {
            viewModel.eventFlow.collect { event ->
                when(event){
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(event.message.asString(context))
                    }
                }
            }
        }

        RecipeListScreen(
            state = state,
            onAction = { action ->
                when(action){
                    is RecipeListScreenAction.OnOpenRecipe ->{
                        backStack.push(NavTarget.DetailScreen(action.recipeId))
                    }
                    else -> viewModel.onAction(action)
                }
            }
        )
    }
}