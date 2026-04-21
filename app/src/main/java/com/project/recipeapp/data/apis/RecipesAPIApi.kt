package com.project.recipeapp.data.apis

import com.project.recipeapp.data.models.RecipeDetail
import com.project.recipeapp.data.models.RecipeListResponse
import retrofit2.http.*
import retrofit2.Response


interface RecipesAPIApi {
    /**
     * GET recipes/{id}
     * Get recipe details
     * 
     * Responses:
     *  - 200: Recipe details
     *  - 404: Recipe not found
     *
     * @param id 
     * @return [RecipeDetail]
     */
    @GET("recipes/{id}")
    suspend fun getRecipeDetail(@Path("id") id: kotlin.String): Response<RecipeDetail>

    /**
     * GET recipes
     * Get paginated list of recipes
     * 
     * Responses:
     *  - 200: List of recipes
     *
     * @param page  (optional, default to 1)
     * @param size  (optional, default to 10)
     * @param likedOnly  (optional, default to false)
     * @return [RecipeListResponse]
     */
    @GET("recipes")
    suspend fun getRecipes(@Query("page") page: kotlin.Int? = 1, @Query("size") size: kotlin.Int? = 10, @Query("likedOnly") likedOnly: kotlin.Boolean? = false): Response<RecipeListResponse>

    /**
     * POST recipes/{id}/like
     * Toggle recipe like status
     * 
     * Responses:
     *  - 200: Recipe like status toggled
     *  - 404: Recipe not found
     *
     * @param id 
     * @return [RecipeDetail]
     */
    @POST("recipes/{id}/like")
    suspend fun toggleRecipeLike(@Path("id") id: kotlin.String): Response<RecipeDetail>

}
