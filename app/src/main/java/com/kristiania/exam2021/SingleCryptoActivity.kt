package com.kristiania.exam2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.kristiania.exam2021.databinding.ActivitySingleCryptoBinding
import com.kristiania.exam2021.viewmodels.CryptoTransactionViewmodel
import com.squareup.picasso.Picasso
import kotlin.math.roundToLong

class SingleCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleCryptoBinding
    private lateinit var viewmodel: CryptoTransactionViewmodel
    private var cryptoName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() //remove the navbar
        viewmodel = CryptoTransactionViewmodel(this)

        //Intent from the recyclerView at our beginning screen
        val startingIntent = intent
        cryptoName = startingIntent.getStringExtra("cryptoName")
        val cryptoValue = startingIntent.getStringExtra("cryptoValue")
        val cryptoSymbol = startingIntent.getStringExtra("cryptoSymbol")

        //Add the intent values to our text views
        binding.cryptoName.text = cryptoName
        binding.cryptoValue.text = "$${cryptoValue}"

        //Load image resource
        Picasso.get().load("https://static.coincap.io/assets/icons/${cryptoSymbol}@2x.png")
            .into(binding.singleCryptoImage)

        //Gets the amount the user has of this coin
        val totalAmount = viewmodel.getTotalOwned(cryptoName!!)
        totalAmount.observe(this) { amount ->
            binding.sellButton.isEnabled = amount > 0
        }

        //Start buy activity
        binding.buyButton.setOnClickListener {
            val intent = Intent(it.context, BuyCryptoActivity::class.java)
            intent.putExtra("coinName", cryptoName)
            intent.putExtra("coinPrice", cryptoValue)
            intent.putExtra("coinSymbol", cryptoSymbol)
            it.context.startActivity(intent)
        }

        //Start sell activity
        binding.sellButton.setOnClickListener {
            val intent = Intent(it.context, SellCryptoActivity::class.java)
            intent.putExtra("coinName", cryptoName)
            intent.putExtra("coinPrice", cryptoValue)
            intent.putExtra("coinSymbol", cryptoSymbol)
            it.context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getTotalOwned(cryptoName!!)
    }
}