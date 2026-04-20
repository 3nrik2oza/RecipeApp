package com.project.recipeapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class InstructionDto(
    val step: Int = 1,
    val description: String = ""
)
