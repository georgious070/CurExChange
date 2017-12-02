package com.examle.curexchange.data.remote;

import com.examle.curexchange.data.model.crypto_code.CryptoCode;
import com.examle.curexchange.data.model.crypto_code.Row;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCryptoCode {

    @GET("currencies")
    Call<CryptoCode> getCryptoCodes();
}
