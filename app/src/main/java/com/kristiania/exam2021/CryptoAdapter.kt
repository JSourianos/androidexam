package com.kristiania.exam2021

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.databinding.ItemCryptoBinding
import java.lang.Math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

//Adapter for our RecyclerView
class CryptoAdapter(
        var cryptos: List<Crypto>
) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {


    inner class CryptoViewHolder(val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.itemView.apply {
            holder.binding.itemCrypto.text = cryptos[position].name //This is the name of the crypto
            holder.binding.cryptoPrice.text = cryptos[position].priceUsd?.toDouble()?.roundToLong().toString() //This is its price in USD (without decimals)

            //Change color of percentage if its less than 0, meaning we have negative returns
            if(cryptos[position].changePercent24Hr?.toDouble()!! < 0) {
                holder.binding.cryptoPercent.setTextColor(resources.getColor(R.color.red))
            } else {
                holder.binding.cryptoPercent.setTextColor(resources.getColor(R.color.green))
            }
            holder.binding.cryptoPercent.text = "${cryptos[position].changePercent24Hr?.toDouble()?.let { it.roundToInt().toString() }}%" //This is the percentage
        }
    }

    override fun getItemCount(): Int {
        return cryptos.size
    }
}