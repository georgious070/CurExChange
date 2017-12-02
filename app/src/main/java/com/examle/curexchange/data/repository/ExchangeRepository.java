package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.database.Cursor;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.CurrencyContract;
import com.examle.curexchange.data.remote.ApiExchange;
import com.examle.curexchange.ui.result.ExchangeCallback;

import javax.inject.Inject;

public class ExchangeRepository {

    private static AsyncQueryHandler handler;
    private ApiExchange apiExchange;

    @Inject
    public ExchangeRepository(ApiExchange apiExchange) {
        this.apiExchange = apiExchange;
    }

    void loadExchangeValue(ExchangeCallback exchangeCallback, String code) {
        apiExchange.getExchange(code, code); //TODO
    }

    void getResult(final ExchangeCallback exchangeCallback, String name) {
        queryCodesFromDB(new QueryCodeCallback() {
            @Override
            public void onSuccess(String code) {
                loadExchangeValue(exchangeCallback, code);
            }
        }, name);
    }

    @SuppressLint("HandlerLeak")
    void queryCodesFromDB(final QueryCodeCallback queryCodeCallback, String name) {

        String[] projection = {CurrencyContract.CurrencyEntry.COLUMN_CODE};
        String selection = CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME + " =?";
        String[] selectionArgs = {name};

        handler = new AsyncQueryHandler(App.getApp().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);

                queryCodeCallback.onSuccess(cursor.toString());
            }
        };

        handler.startQuery(1,
                null,
                CurrencyContract.CurrencyEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }
}
