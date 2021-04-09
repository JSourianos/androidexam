package com.kristiania.exam2021

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Crypto(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String,
    val markedCapUsd: String,
    val volumeUsd24hr: String,
    val priceUsd: String,
    val changePercent24hr: String,
    val vwap24hr: String,
    val explorer: String,
)
