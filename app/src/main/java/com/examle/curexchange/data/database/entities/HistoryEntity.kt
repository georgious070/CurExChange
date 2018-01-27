package com.examle.curexchange.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = HistoryConstants.TABLE_NAME)
data class HistoryEntity(
        @ColumnInfo(name = HistoryConstants.COLUMN_FIRST_CURRENCY)
        var firstCurrencyName: String = "",
        @ColumnInfo(name = HistoryConstants.COLUMN_SECOND_CURRENCY)
        var secondCurrencyName: String = "",
        @ColumnInfo(name = HistoryConstants.COLUMN_RESULT)
        var result: Float = 0f) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = HistoryConstants.COLUMN_ID)
    var id: Long = 0
}