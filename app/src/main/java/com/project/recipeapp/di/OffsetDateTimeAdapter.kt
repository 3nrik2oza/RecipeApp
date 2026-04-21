package com.project.recipeapp.di

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime

object OffsetDateTimeAdapter {
    @FromJson
    fun fromJson(value: String): OffsetDateTime = OffsetDateTime.parse(value)

    @ToJson
    fun toJson(value: OffsetDateTime): String = value.toString()
}