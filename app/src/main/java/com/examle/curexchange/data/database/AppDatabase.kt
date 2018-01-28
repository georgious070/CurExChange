package com.examle.curexchange.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.examle.curexchange.Constants
import com.examle.curexchange.data.database.dao.CurrencyDao
import com.examle.curexchange.data.database.dao.HistoryDao
import com.examle.curexchange.data.database.entities.CurrencyEntity
import com.examle.curexchange.data.database.entities.HistoryEntity

@Database(entities = arrayOf(CurrencyEntity::class, HistoryEntity::class),
        version = Constants.DATABASE_VERSION,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun currencyDao(): CurrencyDao

    abstract fun historyDao(): HistoryDao
}