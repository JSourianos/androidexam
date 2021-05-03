package com.kristiania.exam2021

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ActivitySellCryptoBinding
import com.kristiania.exam2021.viewmodels.CryptoTransactionViewmodel
import java.time.LocalDateTime

class SellCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellCryptoBinding
    private lateinit var transactionViewmodel: CryptoTransactionViewmodel

    @RequiresApi(Build.VERSION_CODES.O)
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
        currentlyOwnedAmount.observe(this){amount ->
            binding.sellBtnCrypto.isEnabled = amount>0
        }
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
            val date: LocalDateTime = LocalDateTime.now()
            val sellAmount = binding.sellEtCrypto.text.toString().toInt()
            val purchasedCryptoEntity = PurchasedCryptoEntity(cryptoName!!, sellAmount*-1, cryptoValue!!.toDouble(), date.toString())
            transactionViewmodel.addCryptoTransaction(purchasedCryptoEntity)
        }
    }
}