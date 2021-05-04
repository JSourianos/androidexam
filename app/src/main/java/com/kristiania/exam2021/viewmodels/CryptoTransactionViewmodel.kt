package com.kristiania.exam2021.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.kristiania.exam2021.api.API
import com.kristiania.exam2021.api.CryptoService
import com.kristiania.exam2021.database.CryptoDao
import com.kristiania.exam2021.database.CryptoDb
import com.kristiania.exam2021.database.PurchasedCryptoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class CryptoTransactionViewmodel(context: Context) : ViewModel() {
    private val cryptoService = API.cryptoService
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

    fun addCryptoTransaction(purchasedCryptoEntity: PurchasedCryptoEntity){
        viewModelScope.launch(Dispatchers.IO){
            cryptoDao.addCryptoTransaction(purchasedCryptoEntity)
        }
    }

    fun getTotalOwned(cryptoName: String): MutableLiveData<Int>{
        viewModelScope.launch(Dispatchers.IO) {
            var amountRet = 0
            val res = cryptoDao.getTransactionHistory()
            res.map { cEl ->
                if(cEl.name == cryptoName) amountRet += cEl.amount
            }
            totalAmount.postValue(amountRet)
        }
        return totalAmount
    }

    /*
    fun getChart(coinSymbol: String){
        viewModelScope.launch(Dispatchers.IO){
            val dataPoints = cryptoService.getGraph(coinSymbol)
            dataPoints.data.map {  dataPoint ->
                series.add(ValueDataEntry("why", 10))
            }
        }
    }


     */
    fun getUserPoints(): Double {
        return userPoints
    }

    fun getChartSeries(): List<DataEntry> {
        return series
    }
}