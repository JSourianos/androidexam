package com.kristiania.exam2021

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.api.CryptoService
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
            if (cryptos[position].changePercent24Hr?.toDouble()!! < 0) {
                holder.binding.cryptoPercent.setTextColor(resources.getColor(R.color.red))
            } else {
                holder.binding.cryptoPercent.setTextColor(resources.getColor(R.color.green))
            }
            holder.binding.cryptoPercent.text = "${cryptos[position].changePercent24Hr?.toDouble()?.let { it.roundToInt().toString() }}%" //This is the percentage
        }

        //This launches the new screen with the single crypto which you clicked on
        holder.itemView.setOnClickListener {
            val currentCryptoName = cryptos[position].name
            val currentCryptoValue = cryptos[position].priceUsd
            //We need to fetch the image from here aswell

            val intent = Intent(it.context, SingleCryptoActivity::class.java)
            intent.putExtra("cryptoName", currentCryptoName)
            intent.putExtra("cryptoValue", currentCryptoValue)
            it.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return cryptos.size
    }
}