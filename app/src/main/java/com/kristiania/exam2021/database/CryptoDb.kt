package com.kristiania.exam2021.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


//const val TABLE_NAME = "PurchasedCrypto"

//This is an entity inside the database.
@Entity
data class CryptoEntity(
    //we need: user points, list of cryptos currently being held, transactions
    @ColumnInfo(name = "userpoints") var userPoints: Int,
    /*
    @ColumnInfo(name = "cryptolist") var cryptoList: List<String>,
    @ColumnInfo(name = "transactions") var transactions: List<String>
     */
) {
    @PrimaryKey(autoGenerate = true)
    var priId: Int? = null

    override fun toString(): String {
        return "You has $userPoints available"
    }
}

//Our db and its entites
@Database(
    entities = [CryptoEntity::class], version = 7, exportSchema = false
)

abstract class CryptoDb : RoomDatabase() {
    abstract fun getDao(): CryptoDao

    companion object {
        var DB_FILENAME = "cryptodb"

        @Volatile
        private var INSTANCE: CryptoDb? = null

        fun get(context: Context): CryptoDb {
            val tmp = INSTANCE
            if (tmp != null) {
                return tmp
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CryptoDb::class.java,
                    DB_FILENAME
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

//This is for access our database. Add the queries here
@Dao
interface CryptoDao {
    //Get user points
    @Query("SELECT userpoints FROM CryptoEntity")
    fun getUserPoints(): LiveData<Int>
/*
    //Get list of crypto (we need to make a new data class for this)
    @Query("SELECT cryptolist FROM CryptoEntity")
    fun getCryptoList(): LiveData<List<String>>

    //Get all previous transactions (need a data class for this aswell)
    @Query("SELECT transactions FROM CryptoEntity")
    fun getTransactions(): LiveData<List<String>>

 */
    @Insert
    fun insertUserPoints(vararg item: CryptoEntity)
}