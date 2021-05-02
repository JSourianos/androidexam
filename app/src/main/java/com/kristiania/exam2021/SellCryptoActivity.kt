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
    }
}