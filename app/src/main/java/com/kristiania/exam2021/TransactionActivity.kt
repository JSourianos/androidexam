package com.kristiania.exam2021

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristiania.exam2021.adapters.TransactionAdapter
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ActivityTransactionBinding
import com.kristiania.exam2021.viewmodels.PortfolioViewModel

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var viewModel: PortfolioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //hide nav

        viewModel = PortfolioViewModel(this)

        val transactionList = mutableListOf<PurchasedCryptoEntity>()
        val adapter = TransactionAdapter(transactionList)
        binding.rvTransactions.adapter = adapter
        binding.rvTransactions.layoutManager = LinearLayoutManager(this)

        viewModel.getListOfTransactions().observe(this) { ownedCryptos ->
            ownedCryptos.map {
                transactionList.add(it)
                adapter.notifyDataSetChanged()
            }
            adapter.notifyDataSetChanged()
        }
    }
}