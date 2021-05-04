package com.kristiania.exam2021.dataclasses
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GraphList(
    val data: List<Graph>
)
