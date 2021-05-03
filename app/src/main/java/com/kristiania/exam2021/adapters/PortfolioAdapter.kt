package com.kristiania.exam2021.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import com.kristiania.exam2021.databinding.ItemCryptoBinding


class PortfolioAdapter(
    var ownedCryptos: List<PurchasedCryptoEntity>
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
            holder.binding.itemCrypto.text = ownedCryptos[position].name
            holder.binding.cryptoPrice.text = ownedCryptos[position].amount.toString()
            holder.binding.cryptoPercent.text = "" // change this later?
        }
    }

    override fun getItemCount(): Int {
        return ownedCryptos.size
    }
}