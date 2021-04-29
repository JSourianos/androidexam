package com.kristiania.exam2021.database

import android.provider.BaseColumns

object DatabaseInfo {

    const val CREATE_TABLE_QUERY = """
        CREATE TABLE ${TableInfo.TABLE_NAME} (
        ${TableInfo.COLUMN_CRYPTO_NAME} TEXT, 
        ${TableInfo.COLUMN_CRYPTO_PRICE} INTEGER,
        ${TableInfo.COLUMN_CRYPTO_AMOUNT} INTEGER,
        ${TableInfo.COLUMN_CRYPTO_BUYDATE} TEXT
    """

    object TableInfo: BaseColumns {
        const val TABLE_NAME = "PurchasedCrypto"
        const val COLUMN_CRYPTO_NAME = "CryptoName"
        const val COLUMN_CRYPTO_PRICE = "Price"
        const val COLUMN_CRYPTO_AMOUNT = "Amount"
        const val COLUMN_CRYPTO_BUYDATE = "Date"
    }
}