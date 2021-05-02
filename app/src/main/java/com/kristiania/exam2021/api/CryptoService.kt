package com.kristiania.exam2021.api

import com.kristiania.exam2021.dataclasses.Data
import com.kristiania.exam2021.dataclasses.DataList
import retrofit2.http.GET
import retrofit2.http.Path

//This is our interface, which we use with Retrofit to define routes for data fetching
interface CryptoService {
    //This returns all crypto currencies
    @GET("v2/assets/")
    suspend fun getAllCryptoCurrencies(): DataList

    //This will return one cryptocurrency based on the id (which is its name)
    @GET("v2/assets/{id}")
    suspend fun getCryptoCurrency(@Path("id") id: String): Data

    //Get image resource
    @GET("https://static.coincap.io/assets/icons/{symbol}@2x.png")
    suspend fun getImage(@Path("symbol") symbol: String)
}