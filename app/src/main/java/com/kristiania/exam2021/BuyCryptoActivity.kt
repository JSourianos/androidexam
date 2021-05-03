package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kristiania.exam2021.databinding.ActivityBuyCryptoBinding

class BuyCryptoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyCryptoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() // hide nav

        val startingIntent = intent
        val cryptoName = startingIntent.getStringExtra("coinName")
        val cryptoValue = startingIntent.getStringExtra("coinPrice")

        binding.tvCryptoName.text = cryptoName
        binding.tvCryptoPrice.text = cryptoValue

        binding.buyTvCrypto.text = cryptoName
        binding.buyEtUsd.isEnabled = false
        binding.buyEtUsd.setText(cryptoValue)
    }
}