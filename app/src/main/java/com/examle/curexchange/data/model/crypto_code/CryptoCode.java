package com.examle.curexchange.data.model.crypto_code;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CryptoCode {

    @SerializedName("rows")
    private List<Row> Rows;

    public List<Row> getRows() {
        return Rows;
    }

    public void setRows(List<Row> rows) {
        Rows = rows;
    }
}
