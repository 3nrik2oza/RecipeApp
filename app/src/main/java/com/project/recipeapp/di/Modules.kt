package com.project.recipeapp.di

import com.project.recipeapp.data.RecipeApi
import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsViewModel
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{


    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.238:4010")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RecipeApi>{
        get<Retrofit>().create(RecipeApi::class.java)
    }

    single { RecipeRepository(get()) }


    viewModel { RecipeListScreenViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }

}