package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.Data
import com.kristiania.exam2021.DataList
import com.kristiania.exam2021.api.API
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.CryptoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//The viewmodel will hold our data, for the activity we use it in
class HomeViewModel(context: Context) : ViewModel() {
    //This is an instance of our service which fetches data from the crypto API
    private val cryptoService = API.cryptoService

    //This will store one the crypto currencies
    val singleCryptoCurrency = MutableLiveData<Data>()

    private val allCryptoCurrencies = MutableLiveData<DataList>()
    private val allImages = MutableLiveData<Unit>()

    //DATABASE
    private var cryptoDao: CryptoDao = CryptoDb.get(context).getDao()
    private lateinit var userPoints: LiveData<Int> //We have the userPoints as LiveData so we can observe them in our HomeActivity

    //This entity represents the 10k which each user "get" when starting our application
    private var entity = CryptoEntity(10000)

    //This function adds the user points to the database when we launch the app
    private fun addUserPoints(entity: CryptoEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                cryptoDao.insertUserPoints(entity)
            }
        }
    }
    //This will run everytime we initialize the Home screen
    init {
        //Use Dispatchers.IO to not block the main UI thread
        viewModelScope.launch(Dispatchers.IO) {

            //This function adds 10k user points when we launch the app
            addUserPoints(entity)

            //This fetches the user points from the Db and adds it to our userPoints variable
            var result = cryptoDao.getUserPoints()
            userPoints = result


            //Get a single crypto
            val cryptos = cryptoService.getCryptoCurrency("bitcoin")
            singleCryptoCurrency.postValue(cryptos)

            //Get all cryptos
            val allCryptos = cryptoService.getAllCryptoCurrencies()
            allCryptoCurrencies.postValue(allCryptos)
        }
    }

    fun getAllCryptos(): MutableLiveData<DataList> {
        return allCryptoCurrencies
    }

    fun getUserPoints(): LiveData<Int>{
        return userPoints
    }

}