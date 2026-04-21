package com.project.recipeapp.presentation.recipe_details


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.recipeapp.R
import com.project.recipeapp.domain.Ingredient
import com.project.recipeapp.domain.Instruction
import com.project.recipeapp.domain.RecipeDetails
import com.project.recipeapp.presentation.asString
import com.project.recipeapp.presentation.recipe_details.components.MessageAlertDialog
import com.project.recipeapp.presentation.recipe_details.components.RecipeDetailsView
import com.project.recipeapp.presentation.components.ErrorView
import com.project.recipeapp.presentation.components.ProgressView
import com.project.recipeapp.ui.theme.RecipeAppTheme


@Composable
fun RecipeDetailsScreen(
    state: RecipeDetailsState,
    onAction: (RecipeDetailsAction) -> Unit,
) {

    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ){

        if(state.showErrorDialog){
            MessageAlertDialog(
                message = state.dialogErrorMessage?.asString() ?: "",
                onDismiss = { onAction(RecipeDetailsAction.OnCloseMessageDialog) }
            )
        }

        Column(
            Modifier.fillMaxSize()
        ) {

            TopBar(
                onBackClick = { onAction(RecipeDetailsAction.OnBackPress) },
                successfullyLoaded = !state.isLoadingInitial && state.errorMessage == null,
                recipeName = state.recipe.name
            )

            when{
                state.isLoadingInitial -> ProgressView()
                state.errorMessage != null -> ErrorView(
                    errorMessage = state.errorMessage.asString(),
                    onRetry = { onAction(RecipeDetailsAction.OnRetryLoading) },
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                )
                else -> RecipeDetailsView(recipe = state.recipe)
            }
        }

        if(!state.isLoadingInitial && state.errorMessage == null){
            FloatingActionButton(
                onClick = { onAction(RecipeDetailsAction.OnLikeToggle) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    painter = if(state.recipe.liked) painterResource(R.drawable.ic_heart)
                    else painterResource(R.drawable.ic_heart_empty),
                    contentDescription = if(state.recipe.liked) stringResource(R.string.favorite)
                    else stringResource(R.string.non_favorite)
                )
            }
        }
    }

}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    successfullyLoaded: Boolean,
    recipeName: String
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackClick() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.go_back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(Modifier.width(10.dp))

        Text(
            text = if(successfullyLoaded) recipeName else "",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}


@Preview
@Composable
private fun Preview() {
    RecipeAppTheme {
        RecipeDetailsScreen(
            state = RecipeDetailsState(
                recipe = RecipeDetails(
                ingredients = listOf(Ingredient("Spaghetti", "400", "g")),
                    instructions = listOf(Instruction(1, "Bring a large pot of salted water to boil and cook spaghetti according to package directions"))
                )),
            onAction = {}
        )
    }
}