package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;

import com.examle.curexchange.data.database.DAOs.CurrencyDao;
import com.examle.curexchange.data.database.DAOs.HistoryDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.database.entities.HistoryEntity;
import com.examle.curexchange.data.remote.ApiExchange;
import com.examle.curexchange.ui.result.ExchangeCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRepository {

    private String firstCode;
    private String secondCode;
    private int value;
    private String firstName;
    private String secondName;
    private ApiExchange apiExchange;
    private HashMap<String, String> mapOfCodeAndName;
    private HistoryDao historyDao;
    private CurrencyDao currencyDao;

    @Inject
    public ExchangeRepository(ApiExchange apiExchange, HistoryDao historyDao, CurrencyDao currencyDao) {
        this.apiExchange = apiExchange;
        this.mapOfCodeAndName = new HashMap<>();
        this.historyDao = historyDao;
        this.currencyDao = currencyDao;
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

    @SuppressLint("StaticFieldLeak")
    private void insertToHistoryTable(String firstName, String secondName, float result) {
        HistoryEntity historyEntity = new HistoryEntity(firstName, secondName, result);
        new AsyncTask<HistoryEntity, Void, Void>() {
            @Override
            protected Void doInBackground(HistoryEntity... historyEntities) {
                historyDao.insertOneRaw(historyEntities[0]);
                return null;
            }
        }.execute(historyEntity);
    }

    @SuppressLint({"HandlerLeak", "StaticFieldLeak"})
    private void queryCodesFromDB(final QueryCodeCallback queryCodeCallback) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Cursor cursor = currencyDao.queryCryptoCodesByCryptoNames(getFirstName(), getSecondName());
                int columnIndexName = cursor
                        .getColumnIndex(CurrencyEntity.CurrencyEntry.COLUMN_CRYPTO_NAME);
                int columnIndexCode = cursor
                        .getColumnIndex(CurrencyEntity.CurrencyEntry.COLUMN_CODE);
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    mapOfCodeAndName.put(cursor.getString(columnIndexName),
                            cursor.getString(columnIndexCode));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                queryCodeCallback.onSuccess(mapOfCodeAndName);
            }
        }.execute();
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
