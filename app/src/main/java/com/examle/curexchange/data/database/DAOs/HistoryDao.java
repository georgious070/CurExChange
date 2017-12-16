package com.examle.curexchange.data.database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.examle.curexchange.data.database.entities.HistoryEntity;

@Dao
public interface HistoryDao {

    @Insert
    void insertOneRaw(HistoryEntity historyEntity);
}
