package com.examle.curexchange.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.examle.curexchange.App;
import com.examle.curexchange.Constants;
import com.examle.curexchange.data.database.AppDatabase;
import com.examle.curexchange.data.database.DAOs.CurrencyDao;
import com.examle.curexchange.data.database.DAOs.HistoryDao;
import com.examle.curexchange.data.remote.ApiCryptoCode;
import com.examle.curexchange.data.remote.ApiExchange;

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
                .build();
    }

    @Provides
    @Singleton
    ApiExchange provideExchangeApi(@Named("exchange") Retrofit retrofit) {
        return retrofit.create(ApiExchange.class);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(App app) {
        return Room
                .databaseBuilder(app, AppDatabase.class, Constants.DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    CurrencyDao provideCurrencyDao(AppDatabase appDatabase) {
        return appDatabase.currencyDao();
    }

    @Provides
    @Singleton
    HistoryDao provideHistoryDao(AppDatabase appDatabase) {
        return appDatabase.historyDao();
    }
}
