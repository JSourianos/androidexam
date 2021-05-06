package com.kristiania.exam2021

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ActivityBuyCryptoBinding
import com.kristiania.exam2021.viewmodels.CryptoTransactionViewmodel
import com.squareup.picasso.Picasso
import java.time.LocalDateTime

class BuyCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyCryptoBinding
    private lateinit var transactionViewmodel: CryptoTransactionViewmodel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() // hide nav

        transactionViewmodel = CryptoTransactionViewmodel(this)

        val startingIntent = intent
        val cryptoName = startingIntent.getStringExtra("coinName")
        val cryptoValue = startingIntent.getStringExtra("coinPrice")
        val cryptoSymbol = startingIntent.getStringExtra("coinSymbol")

        //Top bar showing crypto name and price
        binding.tvCryptoName.text = cryptoName
        binding.tvCryptoPrice.text = cryptoValue
        Picasso.get().load("https://static.coincap.io/assets/icons/${cryptoSymbol}@2x.png")
            .into(binding.buyImageView)

        //Input fields
        binding.buyTvCrypto.text = cryptoName
        binding.buyEtUsd.isEnabled = false
        binding.buyEtUsd.setText(cryptoValue)

        //Function from StackOverflow, used to remove the keyboard after purchase
        //https://stackoverflow.com/questions/41790357/close-hide-the-android-soft-keyboard-with-kotlin
        fun View.hideKeyboard() {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        binding.buyBtnCrypto.setOnClickListener {
            var purchaseAmount: Int = binding.buyEtCrypto.text.toString().toInt()
            Log.d("PURCHASE AMOUNT: ", purchaseAmount.toString())

            val date: LocalDateTime = LocalDateTime.now()

            var userPoints = transactionViewmodel.getUserPoints().toString().toDouble()

            if (userPoints > (purchaseAmount * cryptoValue!!.toDouble())) {
                val purchasedCryptoEntity = PurchasedCryptoEntity(
                    cryptoName!!,
                    purchaseAmount,
                    cryptoValue!!.toDouble(),
                    date.toString(),
                    cryptoSymbol!!.toLowerCase()
                )
                transactionViewmodel.addCryptoTransaction(purchasedCryptoEntity)

                //The USD amount of the sale
                var totalAmount =
                    purchaseAmount.toString().toDouble() * cryptoValue!!.toString().toDouble()

                Toast.makeText(
                    this,
                    "You have purchased $purchaseAmount $cryptoName for ${totalAmount}$",
                    Toast.LENGTH_SHORT
                ).show() //display success toast
                binding.buyEtCrypto.text = null //reset input field
                binding.buyEtCrypto.clearFocus() // clear focus
                it.hideKeyboard() // hide keyboard
            } else {
                Toast.makeText(this, "You dont have sufficient funds", Toast.LENGTH_SHORT)
                    .show() //display error toast
            }

        }
    }
}