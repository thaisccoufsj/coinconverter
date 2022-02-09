package com.sandim.coinconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sandim.coinconverter.data.database.dao.ExchangeDAO
import com.sandim.coinconverter.data.model.ExchangeResponseValue

@Database(entities = [ExchangeResponseValue::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDAO

    companion object {
        fun getInstance(context: Context) : AppDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "exchange_app_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}