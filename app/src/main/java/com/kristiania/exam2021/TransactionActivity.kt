package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kristiania.exam2021.databinding.ActivityTransactionBinding

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //hide nav
    }
}