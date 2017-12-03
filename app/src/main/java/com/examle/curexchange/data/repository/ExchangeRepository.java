package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.database.Cursor;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.CurrencyContract;
import com.examle.curexchange.data.remote.ApiExchange;
import com.examle.curexchange.ui.result.ExchangeCallback;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ExchangeRepository {

    private static AsyncQueryHandler handler;
    private ApiExchange apiExchange;
    private Map<String, String> mapOfCodeAndName;

    @Inject
    public ExchangeRepository(ApiExchange apiExchange) {
        this.apiExchange = apiExchange;
        this.mapOfCodeAndName = new HashMap<>();
    }

    void loadExchangeValue(ExchangeCallback exchangeCallback, Map<String, String> mapOfCodeAndName) {
    }

    public void getResult(final ExchangeCallback exchangeCallback, String firstName, String secondName) {
        queryCodesFromDB(new QueryCodeCallback() {
            @Override
            public void onSuccess(Map<String, String> mapOfCodeAndName) {
                loadExchangeValue(exchangeCallback, mapOfCodeAndName);
            }
        }, firstName, secondName);
    }

    @SuppressLint("HandlerLeak")
    void queryCodesFromDB(final QueryCodeCallback queryCodeCallback, String firstName, String secondName) {

        String[] projection = {CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME,
                CurrencyContract.CurrencyEntry.COLUMN_CODE};
        String selection = CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME + " =?"
                + " OR "
                + CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME + " =?";
        String[] selectionArgc = {firstName, secondName};

        handler = new AsyncQueryHandler(App.getApp().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);

                int columnIndexCode = cursor
                        .getColumnIndex(CurrencyContract.CurrencyEntry.COLUMN_CODE);
                int columnIndexName = cursor
                        .getColumnIndex(CurrencyContract.CurrencyEntry.COLUMN_CRYPTO_NAME);

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    mapOfCodeAndName.put(cursor.getString(columnIndexCode),
                            cursor.getString(columnIndexName));
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
