package com.kristiania.exam2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristiania.exam2021.adapters.CryptoAdapter
import com.kristiania.exam2021.databinding.ActivityHomeBinding
import com.kristiania.exam2021.dataclasses.Crypto
import com.kristiania.exam2021.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //remove the navbar

        viewModel = HomeViewModel(this) // init viewmodel

        //Our list of cryptos
        var cryptoList = mutableListOf<Crypto>()

        //Our RecyclerView
        val adapter = CryptoAdapter(cryptoList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //Display amount of user points the user has
        viewModel.getUserPoints().observe(this) { userPoints ->
            binding.userPointsTv.text = "User Points: ${userPoints?.toString()}"
        }

        //Show users portfolio
        binding.userPointsTv.setOnClickListener {
            val intent = Intent(it.context, PortfolioActivity::class.java)
            it.context.startActivity(intent)
        }

        //This shows all cryptocurrencies
        viewModel.getAllCryptos().observe(this) { crypto ->
            crypto.data.map {
                cryptoList.add(it)
                Log.d("Crypto being mapped: ", it.name!!)
                adapter.notifyDataSetChanged()
                //Maybe use the getImage route with it.symbol.toLowerCase()?
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUpdatedUserPoints()
    }
}