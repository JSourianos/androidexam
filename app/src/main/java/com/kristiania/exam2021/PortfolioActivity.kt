package com.kristiania.exam2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristiania.exam2021.adapters.PortfolioAdapter
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ActivityPortfolioBinding
import com.kristiania.exam2021.viewmodels.PortfolioViewModel

class PortfolioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPortfolioBinding
    private lateinit var viewModel: PortfolioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //hide nav

        viewModel = PortfolioViewModel(this) // initialize our view model

        val ownedCryptoList = mutableListOf<PurchasedCryptoEntity>()
        val adapter = PortfolioAdapter(ownedCryptoList)
        binding.rvPortfolio.adapter = adapter
        binding.rvPortfolio.layoutManager = LinearLayoutManager(this)

        //Add user points to portfolio
        viewModel.getUserPoints().observe(this){ userPoints ->
            binding.portfolioPointsTv.text = "${userPoints?.toString()} USD"
        }

        //Observe on a viewmodel function that returns a list
        viewModel.getListOfCryptos().observe(this){ownedCryptos ->
            ownedCryptos.map {
                ownedCryptoList.add(it)
                Log.d("OWNED CRYPTO: ", it.name)
                adapter.notifyDataSetChanged()
            }
            adapter.notifyDataSetChanged()
        }

        //Move to transaction history
        binding.transactionsButton.setOnClickListener {
            val intent = Intent(it.context, TransactionActivity::class.java)
            it.context.startActivity(intent)
        }
    }
}