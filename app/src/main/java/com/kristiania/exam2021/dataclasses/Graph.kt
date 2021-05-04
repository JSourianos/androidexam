package com.kristiania.exam2021.dataclasses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Graph(
    var priceUsd: String?,
    var time: Double?,
    var date: String?
)
