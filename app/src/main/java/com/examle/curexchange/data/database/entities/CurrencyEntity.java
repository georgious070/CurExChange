package com.examle.curexchange.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = CurrencyEntity.CurrencyEntry.TABLE_NAME)
public class CurrencyEntity {

    public CurrencyEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @PrimaryKey
    @android.support.annotation.NonNull
    @ColumnInfo(name = CurrencyEntry.COLUMN_CODE)
    private String code;

    @ColumnInfo(name = CurrencyEntry.COLUMN_CRYPTO_NAME)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class CurrencyEntry {
        public static final String TABLE_NAME = "crypto_codes";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_CRYPTO_NAME = "name";
    }
}
