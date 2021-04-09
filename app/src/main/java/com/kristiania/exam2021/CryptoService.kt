package com.kristiania.exam2021

import retrofit2.http.GET
import retrofit2.http.Path

//This is our interface, which we use with Retrofit to define routes for data fetching
interface CryptoService {
    //This returns all crypto currencies
    @GET("v2/assets/")
    suspend fun getAllCryptoCurrencies(): Data

    //This will return one cryptocurrency based on the id (which is its name)
    @GET("v2/assets/{id}")
    suspend fun getCryptoCurrency(@Path("id") id: String): Data
}