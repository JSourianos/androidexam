package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.ceil

class CryptoTransactionViewmodel(context: Context) : ViewModel() {
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

    fun getTotalOwned(cryptoName: String): MutableLiveData<Int>{
        val totalAmount = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            var amountRet = 0
            val res = cryptoDao.getOwnedCryptos()
            res.map { cEl ->
                if(cEl.name == cryptoName) amountRet += cEl.amount
            }
            totalAmount.postValue(amountRet)
        }
        return totalAmount
    }

    fun getUserPoints(): Double {
        return userPoints
    }
}