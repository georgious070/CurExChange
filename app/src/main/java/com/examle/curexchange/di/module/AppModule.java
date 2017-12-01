package com.examle.curexchange.di.module;

import android.content.Context;

import com.examle.curexchange.App;
import com.examle.curexchange.Constants;
import com.examle.curexchange.data.remote.CryptoCodeApi;
import com.examle.curexchange.data.remote.ExchangeApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    CryptoCodeApi provideCryptoCodeApi(@Named("codes") Retrofit retrofit) {
        return retrofit.create(CryptoCodeApi.class);
    }

    @Provides
    @Singleton
    @Named("exchange")
    Retrofit provideExchangeRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.EXCHANGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ExchangeApi provideExchangeApi(@Named("exchange") Retrofit retrofit) {
        return retrofit.create(ExchangeApi.class);
    }
}
