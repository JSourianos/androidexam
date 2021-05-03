package com.kristiania.exam2021.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ItemCryptoBinding

class TransactionAdapter(
    var transactions: MutableList<PurchasedCryptoEntity>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.itemView.apply {
            holder.binding.itemCrypto.text = transactions[position].name
            holder.binding.cryptoPrice.text = transactions[position].purchaseDate
            holder.binding.cryptoPercent.text = transactions[position].amount.toString()
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}