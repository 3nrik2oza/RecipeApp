package com.project.recipeapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.project.recipeapp.R
import com.project.recipeapp.domain.ResponseError

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class StringResource(
        val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText
}

@Composable
fun UiText.asString(): String{
    return when(this){
        is UiText.DynamicString -> value
        is UiText.StringResource -> stringResource(id = id, formatArgs = args)
    }
}

fun ResponseError.toErrorMessage(): UiText {
    val stringRes = when(this){
        ResponseError.SERVER_ERROR -> R.string.server_error
        ResponseError.NETWORK_ERROR -> R.string.network_error
    }
    return UiText.StringResource(stringRes)
}