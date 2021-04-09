package com.kristiania.exam2021

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//The viewmodel will hold our data, for the activity we use it in
class HomeViewModel: ViewModel(){
    //This is an instance of our service which fetches data from the crypto API
    val cryptoService = API.cryptoService

    //This will store all the crypto currencies
    val singleCryptoCurrency = MutableLiveData<Data>()

    //This will run everytime we initialize the Home screen
    init {
        //Use Dispatchers.IO to not block the main UI thread
        viewModelScope.launch(Dispatchers.IO) {
            val cryptos = cryptoService.getCryptoCurrency("bitcoin")
            singleCryptoCurrency.postValue(cryptos)
        }
    }
}