package com.kristiania.exam2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.kristiania.exam2021.R
import com.kristiania.exam2021.databinding.ActivitySellCryptoBinding
import com.kristiania.exam2021.viewmodels.CryptoTransactionViewmodel

class SellCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellCryptoBinding
    private lateinit var transactionViewmodel: CryptoTransactionViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //hide nav

        transactionViewmodel = CryptoTransactionViewmodel(this)

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

        //Button init
        binding.sellBtnCrypto.isEnabled = false

        //How many of the crypto you own
        val currentlyOwnedAmount = transactionViewmodel.getTotalOwned(cryptoName!!)
        Toast.makeText(this, currentlyOwnedAmount.toString(), Toast.LENGTH_SHORT).show()

        binding.sellEtCrypto.addTextChangedListener {
            if(binding.sellEtCrypto.text.isNullOrBlank()){
                    binding.sellBtnCrypto.isEnabled = false
            }else{
                val inputValue = binding.sellEtCrypto.text.toString().toInt()
                binding.sellBtnCrypto.isEnabled = inputValue <= currentlyOwnedAmount.value?: 0
            }
        }

        binding.sellBtnCrypto.setOnClickListener {
            //TODO
        }
    }
}