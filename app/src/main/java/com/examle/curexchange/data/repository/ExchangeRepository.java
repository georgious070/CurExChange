package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.database.Cursor;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.CurrencyContract;
import com.examle.curexchange.data.model.cryptonator.Ticker;
import com.examle.curexchange.data.remote.ApiExchange;
import com.examle.curexchange.ui.result.ExchangeCallback;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRepository {

    private static AsyncQueryHandler handler;
    private String multiplier;
    private String firstCode;
    private String secondCode;
    private String firstName;
    private String secondName;
    private ApiExchange apiExchange;
    private HashMap<String, String> mapOfCodeAndName;

    @Inject
    public ExchangeRepository(ApiExchange apiExchange) {
        this.apiExchange = apiExchange;
        this.mapOfCodeAndName = new HashMap<>();
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    private void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void getResult(final ExchangeCallback exchangeCallback,
                          String firstName,
                          String secondName) {
        setFirstName(firstName);
        setSecondName(secondName);
        queryCodesFromDB(new QueryCodeCallback() {
            @Override
            public void onSuccess(HashMap<String, String> mapOfCodeAndName) {
                loadExchangeValue(exchangeCallback, mapOfCodeAndName);
            }
        });
    }

    public void loadExchangeValue(final ExchangeCallback exchangeCallback,
                                  HashMap<String, String> mapOfCodeAndName) {

        firstCode = mapOfCodeAndName.get(getFirstName());
        secondCode = mapOfCodeAndName.get(getSecondName());

        apiExchange.getExchange(firstCode, secondCode).enqueue(new Callback<Ticker>() {
            @Override
            public void onResponse(Call<Ticker> call, Response<Ticker> response) {
                multiplier = response.body().getPrice();

                exchangeCallback.onSuccess();
            }

            @Override
            public void onFailure(Call<Ticker> call, Throwable t) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void queryCodesFromDB(final QueryCodeCallback queryCodeCallback) {

        String[] projection = {CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME,
                CurrencyContract.CurrencyEntry.COLUMN_CODE};
        String selection = CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME + " =?"
                + " OR "
                + CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME + " =?";
        String[] selectionArgc = {getFirstName(), getSecondName()};

        handler = new AsyncQueryHandler(App.getApp().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);

                int columnIndexName = cursor
                        .getColumnIndex(CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME);
                int columnIndexCode = cursor
                        .getColumnIndex(CurrencyContract.CurrencyEntry.COLUMN_CODE);

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    mapOfCodeAndName.put(cursor.getString(columnIndexName),
                            cursor.getString(columnIndexCode));
                }

                queryCodeCallback.onSuccess(mapOfCodeAndName);
            }
        };

        handler.startQuery(1,
                null,
                CurrencyContract.CurrencyEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgc,
                null);
    }
}
