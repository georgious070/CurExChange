package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;

import com.examle.curexchange.App;
import com.examle.curexchange.data.model.pojo.CryptoCode;
import com.examle.curexchange.data.model.pojo.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;
import com.examle.curexchange.data.database.CurrencyContract.CurrencyEntry;
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

    @Inject
    public CurrencyRepository(ApiCryptoCode apiCryptoCode) {
        this.names = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.apiCryptoCode = apiCryptoCode;
    }

    private void loadCurrencyCodes(final WaitForInsertCallback waitForInsertCallback) {
        apiCryptoCode.getCryptoCodes().enqueue(new Callback<CryptoCode>() {
            @Override
            public void onResponse(Call<CryptoCode> call, Response<CryptoCode> response) {
                rows.addAll(response.body().getRows());

                ContentValues[] codesContentValues = new ContentValues[rows.size()];

                ContentValues contentValues = new ContentValues();
                ContentValues helpCV;
                for (int i = 0; i < rows.size(); i++) {
                    contentValues.put(CurrencyEntry.COLUMN_CODE, rows.get(i).getCode());
                    contentValues.put(CurrencyEntry.COLUMN_CRYPTO_NAME, rows.get(i).getName());
                    helpCV = new ContentValues(contentValues);
                    codesContentValues[i]= helpCV;
                }

                MyAsync myAsync = new MyAsync(waitForInsertCallback);
                myAsync.execute(codesContentValues);
            }

            @Override
            public void onFailure(Call<CryptoCode> call, Throwable t) {

            }
        });
    }

    public void getNames(final FirstCurrencyCallback firstCurrencyCallback){
        loadCurrencyCodes(new WaitForInsertCallback() {
            @Override
            public void onSuccess() {
                queryData(firstCurrencyCallback);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void queryData(final FirstCurrencyCallback firstCurrencyCallback) {
        String[] projectionCurrency = {CurrencyEntry.COLUMN_CRYPTO_NAME};

        myHandler = new AsyncQueryHandler(App.getApp().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                   names.add(cursor.getString(cursor.getColumnIndex(CurrencyEntry.COLUMN_CRYPTO_NAME)));
                }
                cursor.close();
                firstCurrencyCallback.onSuccess(names);
            }
        };
        myHandler.startQuery(0, null, CurrencyEntry.CONTENT_URI,
                projectionCurrency,
                null,
                null,
                null);
    }
}
