package com.project.recipeapp.presentation.recipe_details.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.recipeapp.R
import com.project.recipeapp.domain.RecipeDetails

@Composable
fun RecipeDetailsView(
    recipe: RecipeDetails
){
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        item{
            Text(
                text = recipe.shortDescription ?: stringResource(R.string.no_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(vertical = 20.dp)

            )
        }

        item{
            Text(
                text = stringResource(R.string.ingredients),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }

        items(recipe.ingredients){ ingredient ->
            IngredientCard(ingredient)
        }

        item{
            Spacer(Modifier.height(20.dp))
        }


        item{
            Text(
                text = stringResource(R.string.instructions),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }

        items(recipe.instructions){ instruction ->
            InstructionItem(instruction)
        }

        item{
            Spacer(Modifier.height(100.dp))
        }
    }
}