package com.kristiania.exam2021.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ItemCryptoBinding
import com.squareup.picasso.Picasso


class PortfolioAdapter(
    var ownedCryptos: List<CryptoDao.CryptoHolding>
) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {


    inner class PortfolioViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val binding =
            ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PortfolioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.itemView.apply {
            holder.binding.cryptoPrice.setTextSize(20f)
            holder.binding.itemCrypto.text = ownedCryptos[position].cryptoName
            holder.binding.cryptoPrice.setTextSize(16F) // bigger text
            holder.binding.cryptoPrice.text =
                "Current Holding: ${ownedCryptos[position].amount.toString()}"
            holder.binding.cryptoPercent.text = "" // change this later?

            //Fetch image resource
            Picasso.get()
                .load("https://static.coincap.io/assets/icons/${ownedCryptos[position].symbol}@2x.png")
                .into(holder.binding.cryptoImage)
        }
    }

    override fun getItemCount(): Int {
        return ownedCryptos.size
    }
}