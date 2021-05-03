package com.kristiania.exam2021.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


//const val TABLE_NAME = "PurchasedCrypto"

//This is an entity inside the database.

@Entity
data class WalletEntity(
    //we need: user points, list of cryptos currently being held, transactions
    @ColumnInfo(name = "changed") var changed: Double,
) {
    @PrimaryKey(autoGenerate = true)
    var priId: Int? = null
}

@Entity
data class PurchasedCryptoEntity(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "price") var price: Double,
    @ColumnInfo(name = "purchaseDate") var purchaseDate: String
){
    @PrimaryKey(autoGenerate = true)
    var priId: Int? = null
}

//Our db and its entities
@Database(
    entities = [WalletEntity::class, PurchasedCryptoEntity::class], version = 13, exportSchema = false
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
    @Query("SELECT * FROM WalletEntity")
    fun getUserPoints(): List<WalletEntity>

    @Insert
    fun addTransaction(walletEntity: WalletEntity)

    @Insert
    fun addCrypto(purchasedCryptoEntity: PurchasedCryptoEntity)

    fun getTotalUserPoints(): Double{
        var userPoints = getUserPoints()
        var sum: Double = 0.0
        userPoints.map { userPoints ->
            sum += userPoints.changed
        }
        return sum
    }

    fun addCryptoCurrency(purchasedCryptoEntity: PurchasedCryptoEntity){
        var total = purchasedCryptoEntity.amount * purchasedCryptoEntity.price
        var wallet: WalletEntity = WalletEntity(total * -1)
        addTransaction(wallet)
        addCrypto(purchasedCryptoEntity)
    }
}