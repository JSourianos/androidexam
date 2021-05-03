package com.kristiania.exam2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kristiania.exam2021.databinding.ActivitySingleCryptoBinding
import com.kristiania.exam2021.viewmodels.CryptoTransactionViewmodel
import kotlin.math.roundToLong

class SingleCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleCryptoBinding
    private lateinit var viewmodel: CryptoTransactionViewmodel
    private var cryptoName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //remove the navbar

        //Intent from the recyclerView at our beginning screen
        val startingIntent = intent
        cryptoName = startingIntent.getStringExtra("cryptoName")
        val cryptoValue = startingIntent.getStringExtra("cryptoValue")

        //Add the intent values to our text views
        binding.cryptoName.text = cryptoName
        binding.cryptoValue.text = "$${cryptoValue}"


        //Gets the amount the user has of this coin
        viewmodel = CryptoTransactionViewmodel(this)
        val totalAmount = viewmodel.getTotalOwned(cryptoName!!)
        totalAmount.observe(this){amount ->
            binding.sellButton.isEnabled = amount>0
        }

        //Start buy activity
        binding.buyButton.setOnClickListener {
            val intent = Intent(it.context, BuyCryptoActivity::class.java)
            intent.putExtra("coinName", cryptoName)
            intent.putExtra("coinPrice", cryptoValue)
            it.context.startActivity(intent)
        }

        //Start sell activity
        binding.sellButton.setOnClickListener {
            val intent = Intent(it.context, SellCryptoActivity::class.java)
            intent.putExtra("coinName", cryptoName)
            intent.putExtra("coinPrice", cryptoValue)
            it.context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getTotalOwned(cryptoName!!)
    }
}