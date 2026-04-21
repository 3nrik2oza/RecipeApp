package com.project.recipeapp.di

import com.project.recipeapp.data.RecipeRepository
import com.project.recipeapp.data.apis.RecipesAPIApi
import com.project.recipeapp.presentation.recipe_details.RecipeDetailsViewModel
import com.project.recipeapp.presentation.recipe_list.RecipeListScreenViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module{


    single {
        val moshi = Moshi.Builder()
            .add(OffsetDateTimeAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl("http://192.168.1.238:4010")
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .build()
    }

    single<RecipesAPIApi>{
        get<Retrofit>().create(RecipesAPIApi::class.java)
    }

    single { RecipeRepository(get()) }


    viewModel { RecipeListScreenViewModel(get()) }
    viewModel { params ->
        RecipeDetailsViewModel(
            repository = get(),
            recipeId = params.get()
        ) }

}