package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;

import com.examle.curexchange.App;
import com.examle.curexchange.data.remote.ApiExchange;
import com.examle.curexchange.ui.result.ExchangeCallback;
import com.examle.curexchange.data.database.HistoryContract.HistoryEntry;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRepository {

    private static AsyncQueryHandler handler;
    private String firstCode;
    private String secondCode;
    private int value;
    private String firstName;
    private String secondName;
    private ApiExchange apiExchange;
    private HashMap<String, String> mapOfCodeAndName;

    @Inject
    public ExchangeRepository(ApiExchange apiExchange) {
        this.apiExchange = apiExchange;
        this.mapOfCodeAndName = new HashMap<>();
    }

    public void getResult(final ExchangeCallback exchangeCallback,
                          String firstName,
                          String secondName,
                          int value) {
        setFirstName(firstName);
        setSecondName(secondName);
        setValue(value);
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
        String firstLowerCase = firstCode.toLowerCase();
        String secondLowerCase = secondCode.toLowerCase();
        apiExchange.getExchange(firstLowerCase, secondLowerCase).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject ticker = jsonObject.getJSONObject("ticker");
                    String multiplier = ticker.getString("price");
                    exchangeCallback.onSuccess(getResult(multiplier));
                    insertToHistoryTable(getFirstName(), getSecondName(), Float.parseFloat(multiplier));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void insertToHistoryTable(String firstName, String secondName, float result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryEntry.COLUMN_FIRST_CURRENCY, firstName);
        contentValues.put(HistoryEntry.COLUMN_SECOND_CURRENCY, secondName);
        contentValues.put(HistoryEntry.COLUMN_RESULT, result);
        CurrencyAsyncTask currencyAsyncTask = new CurrencyAsyncTask(null, HistoryEntry.TABLE_NAME);
        currencyAsyncTask.execute(contentValues);
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


    private int getResult(String multiplier) {
        int multipl = (int) Float.parseFloat(multiplier);
        return getValue() * multipl;
    }

    private int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    private String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String getSecondName() {
        return secondName;
    }

    private void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
