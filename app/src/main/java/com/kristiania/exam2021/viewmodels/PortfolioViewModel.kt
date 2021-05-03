package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioViewModel(context: Context) : ViewModel() {

    //DATABASE
    private var cryptoDao: CryptoDao = CryptoDb.get(context).getDao()
    private var userPoints: MutableLiveData<Double> =
        MutableLiveData()

    init {
        var availableFunds: Double = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            val result = cryptoDao.getUserPoints()

            result.map { walletEntity ->
                availableFunds += walletEntity.changed
            }

            userPoints.postValue(availableFunds)
        }
    }
    fun getUserPoints(): LiveData<Double> {
        return userPoints
    }
}