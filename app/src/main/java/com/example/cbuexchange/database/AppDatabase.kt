package com.example.cbuexchange.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cbuexchange.dao.CurrencyDao
import com.example.cbuexchange.entity.Currency

@Database(entities = [Currency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao


    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my_currency_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}