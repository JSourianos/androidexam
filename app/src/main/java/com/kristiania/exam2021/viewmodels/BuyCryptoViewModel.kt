package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuyCryptoViewModel(context: Context) : ViewModel() {
    private var cryptoDao: CryptoDao = CryptoDb.get(context).getDao()
    private var userPoints: Double = 0.0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            var result = cryptoDao.getTotalUserPoints()
            userPoints = result
        }
    }

    fun addPurchasedCrypto(purchasedCryptoEntity: PurchasedCryptoEntity){
        viewModelScope.launch(Dispatchers.IO){
            cryptoDao.addCryptoCurrency(purchasedCryptoEntity)
        }
    }

    fun getUserPoints(): Double {
        return userPoints
    }
}