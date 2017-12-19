package com.examle.curexchange.data.repository.currency;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.examle.curexchange.data.database.dao.CurrencyDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.model.pojo.CryptoCode;
import com.examle.curexchange.data.model.pojo.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository {

    private List<String> names;
    private final List<Row> rows;
    private ApiCryptoCode apiCryptoCode;
    private List<CurrencyEntity> currencyEntities;
    private CurrencyDao currencyDao;
    private final Observable<CryptoCode> observableCryptoCode;
    private final Observable<Cursor> observableQuery;

    @Inject
    public CurrencyRepository(ApiCryptoCode apiCryptoCode, CurrencyDao currencyDao) {
        this.currencyEntities = new ArrayList<>();
        this.names = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.apiCryptoCode = apiCryptoCode;
        this.currencyDao = currencyDao;
        this.observableCryptoCode = apiCryptoCode.getCryptoCodes();
        this.observableQuery = currencyDao.queryCryptoNames();

    }

    public Observable<List<String>> getNames() {
        Observable<List<String>> ob = Observable.concat(insertToDbFromNetworkCall(), queryData())
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
        return ob;
    }

    private Observable<CryptoCode> insertToDbFromNetworkCall(){
        return observableCryptoCode.subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<CryptoCode>() {
                    @Override
                    public void accept(CryptoCode cryptoCode) throws Exception {
                        rows.addAll(cryptoCode.getRows());
                        for (int i = 0; i < rows.size(); i++) {
                            currencyEntities.add(new CurrencyEntity(rows.get(i).getCode(), rows.get(i).getName()));
                        }
                        currencyDao.insertAll(currencyEntities);
                    }
                });
    }

    private Observable<List<String>> queryData() {
        Function<Cursor, List<String>> fromCursorToList = new Function<Cursor, List<String>>() {
            @Override
            public List<String> apply(Cursor cursor) throws Exception {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    names.add(cursor.getString(cursor.getColumnIndex(CurrencyEntity.CurrencyEntry.COLUMN_CRYPTO_NAME)));
                }
                return names;
            }
        };
        return observableQuery.map(fromCursorToList)
                .subscribeOn(Schedulers.io());
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
