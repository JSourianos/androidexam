package com.kristiania.exam2021

import retrofit2.http.GET

//This is our interface, which we use with Retrofit to define routes for data fetching
interface CryptoService {
    @GET("cryptocurrencies")
    suspend fun getAllCryptocurrencies(): List<Crypto>
}