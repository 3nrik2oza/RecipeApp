package com.project.recipeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsAction
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsScreen
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class RecipeDetailsScreenNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget>,
    private val recipeId: String
) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
        val viewModel = koinViewModel<RecipeDetailsViewModel>(
            parameters = { parametersOf(recipeId) }
        )
        val state by viewModel.state.collectAsStateWithLifecycle()

        RecipeDetailsScreen(
            state = state,
            onAction = { action ->
                when(action){
                    is RecipeDetailsAction.OnBackPress -> {
                        backStack.pop()
                    }
                    else -> {
                        viewModel.onAction(action)
                    }
                }
            }
        )
    }
}