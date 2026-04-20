package com.project.recipeapp.presentation.recipe_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.recipeapp.R
import com.project.recipeapp.domain.Recipe
import com.project.recipeapp.presentation.UiText
import com.project.recipeapp.presentation.asString
import com.project.recipeapp.presentation.components.ErrorView
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    isLoadingMore: Boolean,
    paginationErrorMessage: UiText?,
    onLoadMore: () -> Unit,
    onOpenRecipe: (String) -> Unit
){
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 3
        }
    }

    LaunchedEffect(listState, isLoadingMore) {
        snapshotFlow { shouldLoadMore }
            .distinctUntilChanged()
            .filter { it && !isLoadingMore && paginationErrorMessage == null}
            .collect { onLoadMore() }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        items(
            items = recipes,
            //key = {recipe -> recipe.id} // Trenutno API vedno vrne istih 10 elementov, med testiranjem pa mi aplikacija crasha
        ){ recipe ->
            RecipeCard(recipe, onOpenRecipe)
        }

        if(isLoadingMore){
            item{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
        }

        if(paginationErrorMessage != null && !isLoadingMore){
            item {
                ErrorView(
                    errorMessage = paginationErrorMessage.asString(),
                    onRetry = { onLoadMore() },
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe, onOpenRecipe: (String) -> Unit){
    ElevatedCard(
        onClick = { onOpenRecipe(recipe.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.padding(5.dp).weight(1f)){
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text= recipe.shortDescription ?: "No description",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Icon(
                painter = if(recipe.liked) painterResource(R.drawable.ic_heart) else painterResource(R.drawable.ic_heart_empty),
                contentDescription = if(recipe.liked) stringResource(R.string.favorite) else stringResource(R.string.non_favorite)
            )

        }
    }
}
