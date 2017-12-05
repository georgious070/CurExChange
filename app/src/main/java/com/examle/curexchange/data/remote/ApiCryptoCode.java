package com.examle.curexchange.data.remote;

import com.examle.curexchange.data.model.pojo.CryptoCode;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCryptoCode {

    @GET("currencies")
    Call<CryptoCode> getCryptoCodes();
}
