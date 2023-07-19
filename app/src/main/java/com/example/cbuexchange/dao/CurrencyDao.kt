package com.example.cbuexchange.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cbuexchange.entity.Currency

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencyList(list: ArrayList<Currency>)

    @Query("select * from currency")
    fun getAllCurrencies(): List<Currency>

    @Update
    fun updateCurrency(currency: Currency)
}