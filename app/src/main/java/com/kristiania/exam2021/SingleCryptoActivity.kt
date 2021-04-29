package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kristiania.exam2021.databinding.ActivitySingleCryptoBinding

class SingleCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleCryptoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val startingIntent = intent
        val cryptoName = startingIntent.getStringExtra("cryptoName")


        binding.cryptoName.text = cryptoName
    }
}