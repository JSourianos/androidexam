package com.kristiania.exam2021.adapters

import android.content.Intent
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.R
import com.kristiania.exam2021.SingleCryptoActivity
import com.kristiania.exam2021.api.CryptoService
import com.kristiania.exam2021.databinding.ItemCryptoBinding
import com.kristiania.exam2021.dataclasses.Crypto
import com.squareup.picasso.Picasso
import kotlin.math.roundToLong

//Adapter for our RecyclerView
class CryptoAdapter(
    var cryptos: List<Crypto>
) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {


    inner class CryptoViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptos: Crypto) {
            binding.itemCrypto.text = cryptos.name //This is the name of the crypto
            binding.cryptoPrice.text =
                "${cryptos.priceUsd.toString()}$" //This is its price in USD (without decimals)
            binding.cryptoPercent.text = "${cryptos.changePercent24Hr}%"

            //Fetch image resource
            Picasso.get()
                .load("https://static.coincap.io/assets/icons/${cryptos.symbol?.toLowerCase()}@2x.png")
                .into(binding.cryptoImage)

            //This launches the new screen with the single crypto which you clicked on
            itemView.setOnClickListener {
                val currentCryptoName = cryptos.name
                val currentCryptoValue = cryptos.priceUsd
                val currentCryptoSymbol = cryptos.symbol?.toLowerCase()

                val intent = Intent(it.context, SingleCryptoActivity::class.java)
                intent.putExtra("cryptoName", currentCryptoName)
                intent.putExtra("cryptoValue", currentCryptoValue)
                intent.putExtra("cryptoSymbol", currentCryptoSymbol)
                it.context.startActivity(intent)
            }

            if (cryptos.changePercent24Hr?.toDouble()!! < 0) {
                binding.cryptoPercent.setTextColor(Color.parseColor("#FF0000"))
            } else {
                binding.cryptoPercent.setTextColor(Color.parseColor("#00FF00"))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptos[position]) //Using the bind method above
    }

    override fun getItemCount(): Int {
        return cryptos.size
    }
}