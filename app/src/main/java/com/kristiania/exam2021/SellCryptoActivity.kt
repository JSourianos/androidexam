package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kristiania.exam2021.R
import com.kristiania.exam2021.databinding.ActivitySellCryptoBinding

class SellCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellCryptoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //hide nav

        val startingIntent = intent
        val cryptoName = startingIntent.getStringExtra("coinName")
        val cryptoValue = startingIntent.getStringExtra("coinPrice")

        //Top bar showing crypto name and price
        binding.tvCryptoName.text = cryptoName
        binding.tvCryptoPrice.text = cryptoValue

        //Input fields
        binding.sellTvCrypto.text = cryptoName
        binding.sellEtUsd.isEnabled = false
        binding.sellEtUsd.setText(cryptoValue)

        binding.sellBtnCrypto.setOnClickListener {

        }
    }
}