package com.examle.curexchange.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.examle.curexchange.data.database.entities.HistoryEntity

@Dao
interface HistoryDao {

    @Insert
    fun insertOneRaw(historyEntity: HistoryEntity)
}