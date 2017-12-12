package com.examle.curexchange.di.module;

import android.content.Context;

import com.examle.curexchange.App;
import com.examle.curexchange.Constants;
import com.examle.curexchange.data.remote.ApiCryptoCode;
import com.examle.curexchange.data.remote.ApiExchange;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private final Context context;

    public AppModule(App context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    @Named("codes")
    Retrofit provideRetrofitCryptoCode() {
        return new Retrofit.Builder()
                .baseUrl(Constants.CRYPTO_CODE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiCryptoCode provideCryptoCodeApi(@Named("codes") Retrofit retrofit) {
        return retrofit.create(ApiCryptoCode.class);
    }

    @Provides
    @Singleton
    @Named("exchange")
    Retrofit provideExchangeRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.EXCHANGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiExchange provideExchangeApi(@Named("exchange") Retrofit retrofit) {
        return retrofit.create(ApiExchange.class);
    }
}
