package com.kristiania.exam2021.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ItemCryptoBinding
import com.squareup.picasso.Picasso

class TransactionAdapter(
    var transactions: MutableList<PurchasedCryptoEntity>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(transactions: PurchasedCryptoEntity){
                binding.itemCrypto.text = transactions.name
                binding.cryptoPrice.text = transactions.purchaseDate
                binding.cryptoPercent.text = transactions.amount.toString()

                //Change color based on if we sold or not
                if(transactions.amount.toString().startsWith("-")){
                    binding.cryptoPercent.setTextColor(Color.parseColor("#FF0000"))
                }

                //Fetch image resource
                Picasso.get().load("https://static.coincap.io/assets/icons/${"btc"}@2x.png").into(binding.cryptoImage)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}