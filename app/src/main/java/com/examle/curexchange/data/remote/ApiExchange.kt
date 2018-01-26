package com.examle.curexchange.data.remote

import io.reactivex.processors.PublishProcessor
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiExchange {

    @GET("full/{base}-{target}")
    fun getExchange(@Path("base") base: String,
                    @Path("target") target: String): PublishProcessor<Any>
}