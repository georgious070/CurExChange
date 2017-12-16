package com.examle.curexchange.data.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class HistoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstCurrencyName;
    private String secondCurrencyName;
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
