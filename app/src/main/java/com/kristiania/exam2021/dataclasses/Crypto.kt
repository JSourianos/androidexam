package com.kristiania.exam2021.dataclasses

import com.squareup.moshi.JsonClass
import java.math.RoundingMode
import java.text.DecimalFormat

@JsonClass(generateAdapter = true)
data class Crypto(
    val id: String?,
    val rank: String?,
    val symbol: String?,
    val name: String?,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val volumeUsd24Hr: String?,
    var priceUsd: String?,
    var changePercent24Hr: String?,
    val vwap24Hr: String?,
    val explorer: String?
    ){
    init {
        //Format the Crypto information to display numbers pretty
        val intPrice: Double? = priceUsd?.toDouble()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        this.priceUsd = df.format(intPrice).toString()

        val intChangePercent = this.changePercent24Hr?.toDouble()
        this.changePercent24Hr = df.format(intChangePercent)
    }
}
