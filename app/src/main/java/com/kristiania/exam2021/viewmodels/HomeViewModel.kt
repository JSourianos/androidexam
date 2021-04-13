package com.kristiania.exam2021.viewmodels

import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristiania.exam2021.Data
import com.kristiania.exam2021.DataList
import com.kristiania.exam2021.api.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//The viewmodel will hold our data, for the activity we use it in
class HomeViewModel : ViewModel() {
    //This is an instance of our service which fetches data from the crypto API
    private val cryptoService = API.cryptoService

    //This will store one the crypto currencies
    val singleCryptoCurrency = MutableLiveData<Data>()

    private val allCryptoCurrencies = MutableLiveData<DataList>()
    private val allImages = MutableLiveData<Unit>()


    //This will run everytime we initialize the Home screen
    init {
        //Use Dispatchers.IO to not block the main UI thread
        viewModelScope.launch(Dispatchers.IO) {
            val cryptos = cryptoService.getCryptoCurrency("bitcoin")
            singleCryptoCurrency.postValue(cryptos)

            val allCryptos = cryptoService.getAllCryptoCurrencies()
            allCryptoCurrencies.postValue(allCryptos)
        }
    }

    fun getAllCryptos(): MutableLiveData<DataList> {
        return allCryptoCurrencies
    }
}