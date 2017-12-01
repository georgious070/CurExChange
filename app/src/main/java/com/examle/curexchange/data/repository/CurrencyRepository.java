package com.examle.curexchange.data.repository;

import android.content.ContentValues;

import com.examle.curexchange.data.model.crypto_code.CryptoCode;
import com.examle.curexchange.data.model.crypto_code.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRepository {

    private final List<Row> rows;
    private ContentValues codesContentValues;
    private ApiCryptoCode apiCryptoCode;

    public CurrencyRepository(ApiCryptoCode apiCryptoCode) {
        this.rows = new ArrayList<>();
        this.apiCryptoCode = apiCryptoCode;
    }

    private void loadDataCurrencyExchange() {
        apiCryptoCode.getCryptoCodes().enqueue(new Callback<CryptoCode>() {
            @Override
            public void onResponse(Call<CryptoCode> call, Response<CryptoCode> response) {
                rows.addAll(response.body().getRows());

                codesContentValues = new ContentValues();
                for (int i = 0; i < rows.size(); i++) {
                    
                }
            }

            @Override
            public void onFailure(Call<CryptoCode> call, Throwable t) {

            }
        });
    }
}
