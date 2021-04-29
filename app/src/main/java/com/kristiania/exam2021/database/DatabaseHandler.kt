package com.kristiania.exam2021.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object DatabaseInfo {
    const val DB_VERSION = 1

    object TableInfo: BaseColumns {
        const val TABLE_NAME = "PurchasedCrypto"
        const val COLUMN_PK = "BuyId"
        const val COLUMN_CRYPTO_NAME = "CryptoName"
        const val COLUMN_CRYPTO_PRICE = "Price"
        const val COLUMN_CRYPTO_AMOUNT = "Amount"
        const val COLUMN_CRYPTO_BUYDATE = "Date"
    }


    const val CREATE_TABLE_QUERY = """
        CREATE TABLE ${TableInfo.TABLE_NAME} (
        ${TableInfo.COLUMN_PK} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${TableInfo.COLUMN_CRYPTO_NAME} TEXT, 
        ${TableInfo.COLUMN_CRYPTO_PRICE} INTEGER,
        ${TableInfo.COLUMN_CRYPTO_AMOUNT} INTEGER,
        ${TableInfo.COLUMN_CRYPTO_BUYDATE} TEXT
    """

    const val DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"

}

class DatabaseHandler (context: Context) : SQLiteOpenHelper (context, "ExamDB", null, DatabaseInfo.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DatabaseInfo.CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}