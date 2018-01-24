package com.examle.curexchange.di.module

import android.arch.persistence.room.Room
import com.examle.curexchange.App
import com.examle.curexchange.Constants
import com.examle.curexchange.data.database.AppDatabase
import com.examle.curexchange.data.database.dao.CurrencyDao
import com.examle.curexchange.data.database.dao.HistoryDao
import com.examle.curexchange.data.remote.ApiCryptoCode
import com.examle.curexchange.data.remote.ApiExchange
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    @Named("codes")
    fun provideRetrofitCryptoCode(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.CRYPTO_CODE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideCryptoCodeApi(@Named("codes") retrofit: Retrofit): ApiCryptoCode {
        return retrofit.create(ApiCryptoCode::class.java)
    }

    @Provides
    @Singleton
    @Named("exchange")
    fun provideExchangeRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.EXCHANGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideExchangeApi(@Named("exchange") retrofit: Retrofit): ApiExchange {
        return retrofit.create(ApiExchange::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: App): AppDatabase {
        return Room
                .databaseBuilder(app, AppDatabase::class.java, Constants.DATABASE_NAME)
                .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }


}