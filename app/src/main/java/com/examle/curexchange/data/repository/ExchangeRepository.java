package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.database.Cursor;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.CurrencyContract;
import com.examle.curexchange.ui.result.ExchangeCallback;

import javax.inject.Inject;

public class ExchangeRepository {

    private static AsyncQueryHandler handler;

    @Inject
    public ExchangeRepository() {
    }

    void loadExchangeValue() {

    }

    void getResult(final ExchangeCallback exchangeCallback, String name) {
        queryCodesFromDB(new QueryCodeCallback() {
            @Override
            public void onSuccess(String code) {

            }
        }, name);
    }

    @SuppressLint("HandlerLeak")
    void queryCodesFromDB(QueryCodeCallback queryCodeCallback, String name) {

        String[] projection = {CurrencyContract.CurrencyEntry.COLUMN_CODE};
        String selection = CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME + " =?";
        String[] selectionArgs = {name};

        handler = new AsyncQueryHandler(App.getApp().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
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
