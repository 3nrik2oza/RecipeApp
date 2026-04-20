package com.project.recipeapp.data.network

import com.project.recipeapp.data.dto.RecipeDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET("/recipes")
    suspend fun getRecipes(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("likedOnly") likedOnly: Boolean = false
    ): Response<RecipesResponse>

    @GET("/recipes/{id}")
    suspend fun getRecipe(@Path("id") id: String): Response<RecipeDetailsDto>

    @POST("/recipes/{id}/like")
    suspend fun postRecipeLike(@Path("id") id: String): Response<RecipeDetailsDto>
}