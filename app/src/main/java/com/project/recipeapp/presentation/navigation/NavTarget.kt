package com.project.recipeapp.presentation.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class NavTarget: Parcelable {
    @Parcelize
    data object ListScreen: NavTarget()

    @Parcelize
    data class DetailScreen(val recipeId: String): NavTarget()

}