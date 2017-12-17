package com.examle.curexchange.data.database;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.RoomDatabase;

import com.examle.curexchange.data.database.dao.CurrencyDao;
import com.examle.curexchange.data.database.dao.HistoryDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.HistoryEntity;

import static com.examle.curexchange.Constants.DATABASE_VERSION;

@Database(entities = {CurrencyEntity.class, HistoryEntity.class}, version = DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CurrencyDao currencyDao();

    public abstract HistoryDao historyDao();
}
