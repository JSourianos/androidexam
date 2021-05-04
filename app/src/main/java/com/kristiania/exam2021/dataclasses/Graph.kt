package com.kristiania.exam2021.dataclasses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Graph(
    val priceUsd: String?,
    var time: Double?,
    val date: String?
)
