package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristiania.exam2021.databinding.ActivityHomeBinding
import com.kristiania.exam2021.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //remove the navbar

        viewModel = HomeViewModel(this)

        //Our list of cryptos
        var cryptoList = mutableListOf<Crypto>()

        //Our RecyclerView
        val adapter = CryptoAdapter(cryptoList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //This brings out a single cryptocurrency
        /*
        viewModel.singleCryptoCurrency.observe(this){ crypto ->
            binding.userPointsTv.text = crypto.data.name
        }
*/
        viewModel.getUserPoints().observe(this) { userPoints ->
            binding.userPointsTv.text = "User Points: ${userPoints?.toString()}"
        }

        //This shows all cryptocurrencies
        viewModel.getAllCryptos().observe(this){ crypto ->
            crypto.data.map {
                cryptoList.add(it)
                Log.d("Crypto being mapped: ", it.name!!)
                adapter.notifyDataSetChanged()
                //Maybe use the getImage route with it.symbol.toLowerCase()?
            }
            adapter.notifyDataSetChanged()
        }
    }
}