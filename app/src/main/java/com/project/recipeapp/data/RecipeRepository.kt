package com.project.recipeapp.data

import com.project.recipeapp.data.apis.RecipesAPIApi
import com.project.recipeapp.data.mappers.toRecipe
import com.project.recipeapp.data.mappers.toRecipeDetails
import com.project.recipeapp.domain.Recipe
import com.project.recipeapp.domain.RecipeDetails
import com.project.recipeapp.domain.ResponseError
import com.project.recipeapp.domain.Result
import com.project.recipeapp.domain.map
import com.project.recipeapp.data.network.safeApiCall

class RecipeRepository(
    private val api: RecipesAPIApi
) {

    suspend fun getRecipes(page: Int = 1, likedOnly: Boolean): Result<List<Recipe>, ResponseError>{
        return safeApiCall { api.getRecipes(page = page, likedOnly = likedOnly) }.map { it.content.map { recipeListItem -> recipeListItem.toRecipe() } }
    }

    suspend fun getRecipe(recipeId: String): Result<RecipeDetails, ResponseError>{
        return safeApiCall { api.getRecipeDetail(recipeId) }.map { it.toRecipeDetails() }
    }

    suspend fun recipeLikeToggle(recipeId: String): Result<RecipeDetails, ResponseError>{
        return safeApiCall { api.toggleRecipeLike(recipeId) } .map { it.toRecipeDetails() }
    }
}