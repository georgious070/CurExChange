package com.examle.curexchange.data.database;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.RoomDatabase;

import com.examle.curexchange.data.database.DAOs.CurrencyDao;
import com.examle.curexchange.data.database.DAOs.HistoryDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.HistoryEntity;

@Database(entities = {CurrencyEntity.class, HistoryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CurrencyDao currencyDao();

    public abstract HistoryDao historyDao();
}
