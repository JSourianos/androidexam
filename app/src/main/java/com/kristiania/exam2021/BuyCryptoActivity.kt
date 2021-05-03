package com.kristiania.exam2021

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ActivityBuyCryptoBinding
import com.kristiania.exam2021.viewmodels.BuyCryptoViewModel
import java.time.LocalDateTime
import java.util.*

class BuyCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyCryptoBinding
    private lateinit var viewModel: BuyCryptoViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() // hide nav

        viewModel = BuyCryptoViewModel(this)

        val startingIntent = intent
        val cryptoName = startingIntent.getStringExtra("coinName")
        val cryptoValue = startingIntent.getStringExtra("coinPrice")

        //Top bar showing crypto name and price
        binding.tvCryptoName.text = cryptoName
        binding.tvCryptoPrice.text = cryptoValue

        //Input fields
        binding.buyTvCrypto.text = cryptoName
        binding.buyEtUsd.isEnabled = false
        binding.buyEtUsd.setText(cryptoValue)


        binding.buyBtnCrypto.setOnClickListener {
            var purchaseAmount: Int = binding.buyEtCrypto.text.toString().toInt()
            Log.d("PURCHASE AMOUNT: ", purchaseAmount.toString())

            val date: LocalDateTime = LocalDateTime.now()

            var userPoints = viewModel.getUserPoints().toString().toDouble()

            if(userPoints > (purchaseAmount * cryptoValue!!.toDouble())){
                val purchasedCryptoEntity = PurchasedCryptoEntity(cryptoName!!, purchaseAmount, cryptoValue!!.toDouble(), date.toString())
                viewModel.addPurchasedCrypto(purchasedCryptoEntity)
            }
        }
    }
}