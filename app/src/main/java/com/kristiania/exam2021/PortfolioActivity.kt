package com.kristiania.exam2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kristiania.exam2021.databinding.ActivityPortfolioBinding

class PortfolioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPortfolioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //hide nav

        binding.transactionsButton.setOnClickListener {
            val intent = Intent(it.context, TransactionActivity::class.java)
            it.context.startActivity(intent)
        }
    }
}