package com.examle.curexchange.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = CurrencyConstants.TABLE_NAME)
data class CurrencyEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = CurrencyConstants.COLUMN_CODE)
        var code: String ="",
        @ColumnInfo(name = CurrencyConstants.COLUMN_CRYPTO_NAME)
        var name: String="")