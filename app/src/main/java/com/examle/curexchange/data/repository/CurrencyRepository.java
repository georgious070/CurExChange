package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.examle.curexchange.data.database.DAOs.CurrencyDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.model.pojo.CryptoCode;
import com.examle.curexchange.data.model.pojo.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;
import com.examle.curexchange.ui.home.FirstCurrencyCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRepository {

    private List<String> names;
    private final List<Row> rows;
    private ApiCryptoCode apiCryptoCode;
    private AsyncQueryHandler myHandler;
    List<CurrencyEntity> currencyEntities;
    private CurrencyDao currencyDao;

    @Inject
    public CurrencyRepository(ApiCryptoCode apiCryptoCode, CurrencyDao currencyDao) {
        this.currencyEntities = new ArrayList<>();
        this.names = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.apiCryptoCode = apiCryptoCode;
        this.currencyDao = currencyDao;
    }

    private void loadCurrencyCodes(final WaitForInsertCallback waitForInsertCallback) {
        apiCryptoCode.getCryptoCodes().enqueue(new Callback<CryptoCode>() {
            @Override
            public void onResponse(Call<CryptoCode> call, Response<CryptoCode> response) {
                rows.addAll(response.body().getRows());
                for (int i = 0; i < rows.size(); i++) {
                    currencyEntities.add(new CurrencyEntity(rows.get(i).getCode(), rows.get(i).getName()));
                }
                CurrencyAsyncTask currencyAsyncTask = new CurrencyAsyncTask(waitForInsertCallback);
                currencyAsyncTask.execute((CurrencyEntity[]) currencyEntities.toArray());
            }

            @Override
            public void onFailure(Call<CryptoCode> call, Throwable t) {

            }
        });
    }

    public void getNames(final FirstCurrencyCallback firstCurrencyCallback) {
        if (isDbEmpty()) {
            loadCurrencyCodes(new WaitForInsertCallback() {
                @Override
                public void onSuccess() {
                    queryData(firstCurrencyCallback);
                }
            });
        } else {
            queryData(firstCurrencyCallback);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void queryData(final FirstCurrencyCallback firstCurrencyCallback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Cursor cursor = currencyDao.queryCryptoNames();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    names.add(cursor.getString(cursor.getColumnIndex(CurrencyEntity.CurrencyEntry.COLUMN_CRYPTO_NAME)));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                firstCurrencyCallback.onSuccess(names);
            }
        }.execute();
    }

    private boolean isDbEmpty() {
        try {
            Cursor c = currencyDao.queryOneLine();
            if (c.moveToFirst()) {
                c.close();
                return false;
            } else {
                c.close();
                return true;
            }
        } catch (SQLiteException e) {
            return true;
        }
    }
}
