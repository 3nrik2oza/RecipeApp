package com.project.recipeapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider

class RootNode(
    buildContext: BuildContext,
    private val snackbarHostState: SnackbarHostState,
    private val backStack: BackStack<NavTarget> = BackStack(
        initialElement = NavTarget.ListScreen,
        savedStateMap = buildContext.savedStateMap
    ),
): ParentNode<NavTarget>(
    buildContext = buildContext,
    navModel = backStack
) {
    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when(navTarget){
            is NavTarget.ListScreen -> ListScreenNode(buildContext, backStack, snackbarHostState)
            is NavTarget.DetailScreen -> RecipeDetailsScreenNode(
                buildContext,
                backStack,
                navTarget.recipeId)
        }
    }

    @Composable
    override fun View(modifier: Modifier){

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ){ innerPadding ->

            Children(
                navModel= backStack,
                transitionHandler = rememberBackstackSlider(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}