package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.chart.common.dataentry.DataEntry
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoTransactionViewmodel(context: Context) : ViewModel() {
    private var cryptoDao: CryptoDao = CryptoDb.get(context).getDao()
    private var userPoints: Double = 0.0
    private var totalAmount = MutableLiveData(0)
    private var series: ArrayList<DataEntry> = ArrayList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            var result = cryptoDao.getTotalUserPoints()
            userPoints = result
        }
    }

    fun addCryptoTransaction(purchasedCryptoEntity: PurchasedCryptoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoDao.addCryptoTransaction(purchasedCryptoEntity)
            var amountRet = 0
            val res = cryptoDao.getTransactionHistory()
            res.map { cEl ->
                if (cEl.name == purchasedCryptoEntity.name) amountRet += cEl.amount
            }
            totalAmount.postValue(amountRet)
        }
    }

    fun getTotalOwned(cryptoName: String): MutableLiveData<Int> {
        viewModelScope.launch(Dispatchers.IO) {
            var amountRet = 0
            val res = cryptoDao.getTransactionHistory()
            res.map { cEl ->
                if (cEl.name == cryptoName) amountRet += cEl.amount
            }
            totalAmount.postValue(amountRet)
        }
        return totalAmount
    }

    fun getUserPoints(): Double {
        return userPoints
    }

    fun getChartSeries(): List<DataEntry> {
        return series
    }
}