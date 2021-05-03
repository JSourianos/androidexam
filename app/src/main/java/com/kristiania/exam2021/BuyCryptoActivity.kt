package com.kristiania.exam2021

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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

        //Function from StackOverflow, used to remove the keyboard after purchase
        //https://stackoverflow.com/questions/41790357/close-hide-the-android-soft-keyboard-with-kotlin
        fun View.hideKeyboard() {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        binding.buyBtnCrypto.setOnClickListener {
            var purchaseAmount: Int = binding.buyEtCrypto.text.toString().toInt()
            Log.d("PURCHASE AMOUNT: ", purchaseAmount.toString())

            val date: LocalDateTime = LocalDateTime.now()

            var userPoints = viewModel.getUserPoints().toString().toDouble()

            if(userPoints > (purchaseAmount * cryptoValue!!.toDouble())){
                val purchasedCryptoEntity = PurchasedCryptoEntity(cryptoName!!, purchaseAmount, cryptoValue!!.toDouble(), date.toString())
                viewModel.addPurchasedCrypto(purchasedCryptoEntity)

                Toast.makeText(this, "You have purchased $purchaseAmount $cryptoName for $cryptoValue", Toast.LENGTH_SHORT).show() //display success toast
                binding.buyEtCrypto.text = null //reset input field
                binding.buyEtCrypto.clearFocus() // clear focus
                it.hideKeyboard() // hide keyboard
            }
        }
    }
}