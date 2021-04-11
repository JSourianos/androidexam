package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristiania.exam2021.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //For testing the recyclerview
        var cryptoList = mutableListOf<Crypto>()

        val adapter = CryptoAdapter(cryptoList)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        //This brings out a single cryptocurrency
        viewModel.singleCryptoCurrency.observe(this){ crypto ->
            binding.tvTest.text = crypto.data.name
        }

        //This shows all cryptocurrencies (only selecting the first one in the array to not make the screen to full)
        viewModel.allCryptoCurrencies.observe(this){ crypto ->

            cryptoList.add(crypto.data[5])
            cryptoList.add(crypto.data[1])

            binding.tvTestMultiple.text = cryptoList[1].toString()
        }

        //We now need to create a RecyclerList, and fragments to represent each individual crypto currency.
        // 1) RecyclerView with Fragment
        // 2) Loop over allCryptoCurrency array, and populate the fragments with each object we retrieve from the API
        // 3) Show this to the user
        // 4) When clicking on an item in the list, navigate to a new screen (with data about the selected cryptocurrency)
            //I think this can be achieved by grabbing the name, and then using the singleCrypto GET request to retrieve all data about it
    }
}