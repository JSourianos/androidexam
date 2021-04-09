package com.kristiania.exam2021

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//We just create the instances of moshi and retrofit statically
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://api.coincap.io/")
    .build()

object API {
    val cryptoService: CryptoService by lazy {
        retrofit.create(CryptoService::class.java)
    }
}