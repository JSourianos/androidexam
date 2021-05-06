package com.kristiania.exam2021.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ItemCryptoBinding
import com.squareup.picasso.Picasso
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TransactionAdapter(
    var transactions: MutableList<PurchasedCryptoEntity>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(transactions: PurchasedCryptoEntity) {
            binding.itemCrypto.text = transactions.name
            binding.cryptoPrice.text = LocalDateTime.parse(transactions.purchaseDate)
                .format(DateTimeFormatter.ofPattern("MMMM d, yyyy, H:m"))
            if (transactions.amount < 0) {
                var amount = transactions.amount.toString().replace("-", "") //remove minus
                binding.cryptoPercent.text = "Sold: ${amount}"
            } else {
                binding.cryptoPercent.text = "Bought: ${transactions.amount.toString()}"
            }

            //Change color based on if we sold or not
            if (transactions.amount.toString().startsWith("-")) {
                binding.cryptoPercent.setTextColor(Color.parseColor("#FF0000"))
            }

            //Fetch image resource
            Picasso.get()
                .load("https://static.coincap.io/assets/icons/${transactions.symbol.toLowerCase()}@2x.png")
                .into(binding.cryptoImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}