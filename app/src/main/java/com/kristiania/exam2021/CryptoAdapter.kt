package com.kristiania.exam2021

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.databinding.ItemCryptoBinding

//Adapter for our RecyclerView
class CryptoAdapter (
    var cryptos: List<String>
    ) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>(){


    inner class CryptoViewHolder(val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.itemView.apply {
            holder.binding.itemCrypto.text = cryptos[position]
        }
    }

    override fun getItemCount(): Int {
        return cryptos.size
    }
}