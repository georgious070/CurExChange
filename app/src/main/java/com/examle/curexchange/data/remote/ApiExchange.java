package com.examle.curexchange.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExchange {

    @GET("full/{base}-{target}")
    Call<Object> getExchange(@Path("base") String base,
                             @Path("target") String usd);
}
