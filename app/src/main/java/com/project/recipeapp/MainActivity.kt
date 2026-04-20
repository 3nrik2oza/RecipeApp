package com.project.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.recipeapp.presentation.UiEvent
import com.project.recipeapp.presentation.asString
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsAction
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsScreen
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsViewModel
import com.project.recipeapp.presentation.recipe_list.RecipeListScreen
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenAction
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenViewModel
import com.project.recipeapp.ui.theme.RecipeAppTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            RecipeAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.ListScreen,
                        modifier = Modifier.padding(innerPadding)
                    ){

                        composable<Route.ListScreen>{
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
                                            navController.navigate(Route.DetailScreen(action.recipeId))
                                        }
                                        else -> {
                                            viewModel.onAction(action)
                                        }
                                    }
                                }
                            )
                        }

                        composable<Route.DetailScreen> {
                            val viewModel = koinViewModel<RecipeDetailsViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            RecipeDetailsScreen(
                                state = state,
                                onAction = { action ->
                                    when(action){
                                        is RecipeDetailsAction.OnBackPress -> {
                                            navController.popBackStack()
                                        }
                                        else -> {
                                            viewModel.onAction(action)
                                        }
                                    }
                                }
                            )
                        }

                    }
                }
            }

        }
    }
}

sealed interface Route {
    @Serializable
    object ListScreen: Route

    @Serializable
    data class DetailScreen(val recipeId: String): Route
}