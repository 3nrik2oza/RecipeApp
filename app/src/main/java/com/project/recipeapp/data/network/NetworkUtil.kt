package com.project.recipeapp.data.network

import com.project.recipeapp.domain.ResponseError
import com.project.recipeapp.domain.Result
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response


suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T, ResponseError> {
    return try {
        val response = apiCall()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(ResponseError.SERVER_ERROR)
        }
    } catch (e: HttpException) {
        Result.Error(ResponseError.SERVER_ERROR)
    } catch (e: IOException) {
        Result.Error(ResponseError.NETWORK_ERROR)
    }
}