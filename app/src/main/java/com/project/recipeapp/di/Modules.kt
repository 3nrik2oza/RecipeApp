package com.project.recipeapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.recipeapp.data.network.RecipeApi
import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsViewModel
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module{


    single {
        val networkJson = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("http://192.168.1.238:4010")
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<RecipeApi>{
        get<Retrofit>().create(RecipeApi::class.java)
    }

    single { RecipeRepository(get()) }


    viewModel { RecipeListScreenViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get(), get()) }

}