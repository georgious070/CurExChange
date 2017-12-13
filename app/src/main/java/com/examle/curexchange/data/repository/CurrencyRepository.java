package com.examle.curexchange.data.repository;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.drm.DrmStore;

import com.examle.curexchange.App;
import com.examle.curexchange.data.database.DbHelper;
import com.examle.curexchange.data.model.pojo.CryptoCode;
import com.examle.curexchange.data.model.pojo.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;
import com.examle.curexchange.data.database.CurrencyContract.CurrencyEntry;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;
import io.reactivex.schedulers.Schedulers;

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

    private Observable<CryptoCode> loadCurrencyCodes() {
        return apiCryptoCode.getCryptoCodes()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<CryptoCode>() {
                    @Override
                    public void accept(CryptoCode cryptoCode) throws Exception {
                        rows.addAll(cryptoCode.getRows());
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
                    }
                });
    }

    public Observable<List<String>> getNames() {
        if (isDbEmpty(sqLiteDatabase, CurrencyEntry.TABLE_NAME)) {
            loadCurrencyCodes().observeOn(AndroidSchedulers.mainThread()).subscribe();
            return queryData().observeOn(AndroidSchedulers.mainThread());

        } else {
            return queryData().observeOn(AndroidSchedulers.mainThread());
        }
    }

    @SuppressLint("HandlerLeak")
    private Observable<List<String>> queryData() {

        final String[] projectionCurrency = {CurrencyEntry.COLUMN_CRYPTO_NAME};
        Observable<List<String>> observable = Observable.fromArray();
        observable
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        Cursor cursor = App.getApp().getContentResolver().query(CurrencyEntry.CONTENT_URI,
                                projectionCurrency,
                                null,
                                null,
                                null);

                        for (int i = 0; i < cursor.getCount(); i++) {
                            cursor.moveToNext();
                            names.add(cursor.getString(cursor.getColumnIndex(CurrencyEntry.COLUMN_CRYPTO_NAME)));
                        }
                        cursor.close();

                    }
                })
                .subscribeOn(Schedulers.io());
        return observable;
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
