package com.examle.curexchange.data.remote;

import com.examle.curexchange.data.model.pojo.CryptoCode;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCryptoCode {

    @GET("currencies")
    Flowable<CryptoCode> getCryptoCodes();
}
