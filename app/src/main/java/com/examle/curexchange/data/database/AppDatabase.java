package com.examle.curexchange.data.database;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.RoomDatabase;

import com.examle.curexchange.Constants;
import com.examle.curexchange.data.database.dao.CurrencyDao;
import com.examle.curexchange.data.database.dao.HistoryDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.HistoryEntity;

@Database(entities = {CurrencyEntity.class, HistoryEntity.class},
        version = Constants.DATABASE_VERSION,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CurrencyDao currencyDao();

    public abstract HistoryDao historyDao();
}
