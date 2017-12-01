package com.examle.curexchange.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoCodeApi {

    @GET("currencies")
    Call<String> getCryptoCodes();
}
