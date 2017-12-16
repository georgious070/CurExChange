package com.examle.curexchange.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

@Entity(tableName = "history")
public class HistoryEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    private int id;

    @ColumnInfo(name = "first")
    private String firstCurrencyName;

    @ColumnInfo(name = "second")
    private String secondCurrencyName;

    @ColumnInfo(name = "result")
    private long result;

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

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }
}
