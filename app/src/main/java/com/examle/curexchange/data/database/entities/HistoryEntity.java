package com.examle.curexchange.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

@Entity(tableName = HistoryEntity.HistoryEntry.TABLE_NAME)
public class HistoryEntity {

    public HistoryEntity(String firstCurrencyName, String secondCurrencyName, float result) {
        this.firstCurrencyName = firstCurrencyName;
        this.secondCurrencyName = secondCurrencyName;
        this.result = result;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = BaseColumns._ID)
    private int id;

    @ColumnInfo(name = HistoryEntry.COLUMN_FIRST_CURRENCY)
    private String firstCurrencyName;

    @ColumnInfo(name = HistoryEntry.COLUMN_SECOND_CURRENCY)
    private String secondCurrencyName;

    @ColumnInfo(name = HistoryEntry.COLUMN_RESULT)
    private float result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstCurrencyName() {
        return firstCurrencyName;
    }

    public void setFirstCurrencyName(String firstCurrencyName) {
        this.firstCurrencyName = firstCurrencyName;
    }

    public String getSecondCurrencyName() {
        return secondCurrencyName;
    }

    public void setSecondCurrencyName(String secondCurrencyName) {
        this.secondCurrencyName = secondCurrencyName;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    static class HistoryEntry implements BaseColumns {
        static final String TABLE_NAME = "history";
        static final String COLUMN_FIRST_CURRENCY = "first";
        static final String COLUMN_SECOND_CURRENCY = "second";
        static final String COLUMN_RESULT = "result";
    }
}
