package com.kristiania.exam2021

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ActivitySellCryptoBinding
import com.kristiania.exam2021.viewmodels.CryptoTransactionViewmodel
import com.squareup.picasso.Picasso
import java.time.LocalDateTime

class SellCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellCryptoBinding
    private lateinit var transactionViewmodel: CryptoTransactionViewmodel
    private lateinit var currentlyOwnedAmount: MutableLiveData<Int>

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
        val cryptoSymbol = startingIntent.getStringExtra("coinSymbol")

        //Top bar showing crypto name and price
        binding.tvCryptoName.text = cryptoName
        binding.tvCryptoPrice.text = cryptoValue
        Picasso.get().load("https://static.coincap.io/assets/icons/${cryptoSymbol}@2x.png")
            .into(binding.selImageView)


        //Input fields
        binding.sellEtUsd.isEnabled = false
        binding.sellEtUsd.setText(cryptoValue)
        binding.holdingAmount.isEnabled = false

        //Function from StackOverflow, used to remove the keyboard after purchase
        //https://stackoverflow.com/questions/41790357/close-hide-the-android-soft-keyboard-with-kotlin
        fun View.hideKeyboard() {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        //Button init
        binding.sellBtnCrypto.isEnabled = false

        //How many of the crypto you own
        currentlyOwnedAmount = transactionViewmodel.getTotalOwned(cryptoName!!)
        currentlyOwnedAmount.observe(this) { amount ->
            binding.sellBtnCrypto.isEnabled = amount > 0
            binding.holdingAmount.setText(amount.toString())
        }

        binding.sellEtCrypto.addTextChangedListener {
            if (binding.sellEtCrypto.text.isNullOrBlank()) {
                binding.sellBtnCrypto.isEnabled = false
            } else {
                val inputValue = binding.sellEtCrypto.text.toString().toInt()
                binding.sellBtnCrypto.isEnabled =
                    inputValue <= currentlyOwnedAmount.value ?: 0 && currentlyOwnedAmount.value!! > 0
            }
        }

        binding.sellBtnCrypto.setOnClickListener {
            val date: LocalDateTime = LocalDateTime.now()
            val sellAmount = binding.sellEtCrypto.text.toString().toInt()
            val purchasedCryptoEntity = PurchasedCryptoEntity(
                cryptoName!!,
                sellAmount * -1,
                cryptoValue!!.toDouble(),
                date.toString(),
                cryptoSymbol!!.toLowerCase()
            )
            transactionViewmodel.addCryptoTransaction(purchasedCryptoEntity)

            //The USD amount of the sale
            var totalAmount = sellAmount.toString().toDouble() * cryptoValue!!.toString().toDouble()

            Toast.makeText(
                this,
                "You have sold $sellAmount $cryptoName for ${(totalAmount)}$",
                Toast.LENGTH_SHORT
            ).show() //display success toast
            binding.sellEtCrypto.text = null //reset input field
            binding.sellEtCrypto.clearFocus() // clear focus
            it.hideKeyboard() // hide keyboard
        }
    }
}