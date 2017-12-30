package com.examle.curexchange.data.remote;

import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExchange {

    @GET("full/{base}-{target}")
    PublishProcessor<Object> getExchange(@Path("base") String base,
                                         @Path("target") String usd);
}
