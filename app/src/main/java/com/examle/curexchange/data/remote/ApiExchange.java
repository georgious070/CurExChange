package com.examle.curexchange.data.remote;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExchange {

    @GET("{base}-{target}")
    Call<String> getExchange(@Path("base") String base,
                             @Path("target") String target);
}
