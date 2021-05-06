package com.kristiania.exam2021.dataclasses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataList(
    val data: List<Crypto>
)