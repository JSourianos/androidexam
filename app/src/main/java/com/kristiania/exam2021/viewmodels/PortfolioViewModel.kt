package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.api.API.cryptoService
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioViewModel(context: Context) : ViewModel() {

    //DATABASE
    private var cryptoDao: CryptoDao = CryptoDb.get(context).getDao()
    private var userPoints: MutableLiveData<Double> =
        MutableLiveData()
    private var ownedCryptoList: MutableLiveData<List<CryptoDao.CryptoHolding>> =
        MutableLiveData<List<CryptoDao.CryptoHolding>>()
    private var transactionList = MutableLiveData<List<PurchasedCryptoEntity>>()

    init {
        var availableFunds: Double = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            //Get user points
            val result = cryptoDao.getUserPoints()

            result.map { walletEntity ->
                availableFunds += walletEntity.changed
            }

            userPoints.postValue(availableFunds)

            val listOfCryptos = cryptoDao.getTotalCryptoHolding()
            ownedCryptoList.postValue(listOfCryptos)

            transactionList.postValue(cryptoDao.getTransactionHistory())
        }
    }

    fun getListOfCryptos(): MutableLiveData<List<CryptoDao.CryptoHolding>> {
        return ownedCryptoList
    }

    fun getListOfTransactions(): MutableLiveData<List<PurchasedCryptoEntity>> {
        return transactionList
    }

    fun getUserPoints(): LiveData<Double> {
        return userPoints
    }
}