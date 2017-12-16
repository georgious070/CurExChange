package com.examle.curexchange.data.database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.CurrencyEntity.CurrencyEntry;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CurrencyEntity> currencies);

    @Query("SELECT * FROM " + CurrencyEntry.TABLE_NAME + " LIMIT 1")
    Cursor queryOneLine();

    @Query("SELECT " + CurrencyEntry.COLUMN_CRYPTO_NAME + " FROM " + CurrencyEntry.TABLE_NAME)
    Cursor queryCryptoNames();
}
