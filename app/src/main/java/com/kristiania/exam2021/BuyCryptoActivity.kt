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
    }
}