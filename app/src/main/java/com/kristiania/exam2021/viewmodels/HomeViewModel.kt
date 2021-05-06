package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.api.API
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.WalletEntity
import com.kristiania.exam2021.dataclasses.DataList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//The viewmodel will hold our data, for the activity we use it in
class HomeViewModel(context: Context) : ViewModel() {
    //This is an instance of our service which fetches data from the crypto API
    private val cryptoService = API.cryptoService

    private val allCryptoCurrencies = MutableLiveData<DataList>()

    //DATABASE
    private var cryptoDao: CryptoDao = CryptoDb.get(context).getDao()
    private var userPoints: MutableLiveData<Double> =
        MutableLiveData(0.0) //We have the userPoints as LiveData so we can observe them in our HomeActivity

    //This will run everytime we initialize the Home screen
    init {
        var availableFunds: Double = 0.0
        //Use Dispatchers.IO to not block the main UI thread
        viewModelScope.launch(Dispatchers.IO) {

            //This fetches the user points from the Db and adds it to our userPoints variable
            var result = cryptoDao.getUserPoints()

            //Add 10k to user if the user has no transactions
            if (result.isEmpty()) {
                var walletEntity: WalletEntity = WalletEntity(10000.0)
                cryptoDao.addTransaction(walletEntity)
                result = cryptoDao.getUserPoints()
            }

            result.map { walletEntity ->
                availableFunds += walletEntity.changed
            }

            userPoints.postValue(availableFunds)

            //Get all cryptos
            val allCryptos = cryptoService.getAllCryptoCurrencies()
            allCryptoCurrencies.postValue(allCryptos)

        }
    }

    fun getAllCryptos(): MutableLiveData<DataList> {
        return allCryptoCurrencies
    }

    fun getUserPoints(): LiveData<Double> {
        return userPoints
    }

    //Update user points when we resume HomeActivity++
    fun getUpdatedUserPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            userPoints.postValue(cryptoDao.getTotalUserPoints())
        }
    }
}