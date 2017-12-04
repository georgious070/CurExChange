package com.examle.curexchange.data.remote;


import com.examle.curexchange.data.model.cryptonator.Ticker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiExchange {

    @GET("{base}-{target}")
    Call<Ticker> getExchange(@Query("base") String base,
                             @Query("target") String target);
}
