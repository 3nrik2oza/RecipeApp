package com.project.recipeapp.data

import com.project.recipeapp.domain.Recipe
import com.project.recipeapp.domain.RecipeDetails
import com.project.recipeapp.domain.ResponseError
import com.project.recipeapp.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class RecipeRepository(
    private val api: RecipeApi
) {

    suspend fun getRecipes(page: Int = 1, likedOnly: Boolean): Result<List<Recipe>, ResponseError> = withContext(Dispatchers.IO){
        val response = try {
            api.getRecipes(page = page, likedOnly = likedOnly)
        } catch (_: HttpException) {
            return@withContext Result.Error(ResponseError.SERVER_ERROR)
        } catch (_: IOException) {
            return@withContext Result.Error(ResponseError.NETWORK_ERROR)
        }
        if(!response.isSuccessful || response.body() == null){
            return@withContext Result.Error(ResponseError.SERVER_ERROR)
        }

        return@withContext Result.Success(response.body()!!.content)
    }

    suspend fun getRecipe(recipeId: String): Result<RecipeDetails, ResponseError> = withContext(Dispatchers.IO){
        val response = try {
            api.getRecipe(recipeId)
        }catch (_: HttpException) {
            return@withContext Result.Error(ResponseError.SERVER_ERROR)
        } catch (_: IOException) {
            return@withContext Result.Error(ResponseError.NETWORK_ERROR)
        }
        if(!response.isSuccessful || response.body() == null){
            return@withContext Result.Error(ResponseError.SERVER_ERROR)
        }

        return@withContext Result.Success(response.body()!!)
    }



    suspend fun recipeLikeToggle(recipeId: String): Result<RecipeDetails, ResponseError> = withContext(Dispatchers.IO){
        val response = try {
            api.postRecipeLike(recipeId)
        }catch (_: HttpException) {
            return@withContext Result.Error(ResponseError.SERVER_ERROR)
        } catch (_: IOException) {
            return@withContext Result.Error(ResponseError.NETWORK_ERROR)
        }
        if(!response.isSuccessful || response.body() == null){
            return@withContext Result.Error(ResponseError.SERVER_ERROR)
        }

        return@withContext Result.Success(response.body()!!)
    }
}