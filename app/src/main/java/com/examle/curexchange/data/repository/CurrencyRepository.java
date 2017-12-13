package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.DbHelper;
import com.examle.curexchange.data.model.pojo.CryptoCode;
import com.examle.curexchange.data.model.pojo.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;
import com.examle.curexchange.data.database.CurrencyContract.CurrencyEntry;
import com.examle.curexchange.ui.home.FirstCurrencyCallback;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRepository {

    private List<String> names;
    private final List<Row> rows;
    private ApiCryptoCode apiCryptoCode;
    private AsyncQueryHandler myHandler;
    private SQLiteDatabase sqLiteDatabase;
    private DbHelper dbHelper;

    @Inject
    public CurrencyRepository(ApiCryptoCode apiCryptoCode) {
        this.names = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.apiCryptoCode = apiCryptoCode;
        this.dbHelper = new DbHelper(App.getApp());
        this.sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    private void loadCurrencyCodes(final WaitForInsertCallback waitForInsertCallback) {
        Observable<CryptoCode> observable = apiCryptoCode.getCryptoCodes();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CryptoCode>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CryptoCode value) {
                        rows.addAll(value.getRows());
                        ContentValues[] codesContentValues = new ContentValues[rows.size()];
                        ContentValues contentValues = new ContentValues();
                        ContentValues helpCV;
                        for (int i = 0; i < rows.size(); i++) {
                            contentValues.put(CurrencyEntry.COLUMN_CODE, rows.get(i).getCode());
                            contentValues.put(CurrencyEntry.COLUMN_CRYPTO_NAME, rows.get(i).getName());
                            helpCV = new ContentValues(contentValues);
                            codesContentValues[i] = helpCV;
                        }
                        App.getApp().getContentResolver().bulkInsert(CurrencyEntry.CONTENT_URI, codesContentValues);
//                        MyAsync myAsync = new MyAsync(waitForInsertCallback, CurrencyEntry.TABLE_NAME);
//                        myAsync.execute(codesContentValues);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getNames(final FirstCurrencyCallback firstCurrencyCallback) {
        if (isDbEmpty(sqLiteDatabase, CurrencyEntry.TABLE_NAME)) {
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

    private boolean isDbEmpty(SQLiteDatabase db, String tableName) {
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 1", null);
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
