package com.examle.curexchange.data.repository.currency;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;

import com.examle.curexchange.data.database.dao.CurrencyDao;
import com.examle.curexchange.data.database.entities.CurrencyEntity;
import com.examle.curexchange.data.model.pojo.CryptoCode;
import com.examle.curexchange.data.model.pojo.Row;
import com.examle.curexchange.data.remote.ApiCryptoCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository {

    private List<String> names;
    private final List<Row> rows;
    private ApiCryptoCode apiCryptoCode;
    private List<CurrencyEntity> currencyEntities;
    private CurrencyDao currencyDao;
    private final Flowable<CryptoCode> observableCryptoCode;
    private final Flowable<List<String>> observableQuery;

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

    public Flowable<List<String>> getNames() {
        Flowable<List<String>> ob = Flowable.concat(insertToDbFromNetworkCall(), queryData())
                .subscribeOn(Schedulers.io());
        return ob;
    }

    private Flowable<List<String>> insertToDbFromNetworkCall() {
        Flowable<List<String>> ob = observableCryptoCode
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<CryptoCode, Flowable<List<String>>>() {
                    @Override
                    public Flowable<List<String>> apply(CryptoCode cryptoCode) throws Exception {
                        rows.addAll(cryptoCode.getRows());
                        for (int i = 0; i < rows.size(); i++) {
                            currencyEntities.add(new CurrencyEntity(rows.get(i).getCode(), rows.get(i).getName()));
                        }
                        currencyDao.insertAll(currencyEntities);
                        return null;
                    }
                }).observeOn(Schedulers.computation());

        return ob;
    }

    private Flowable<List<String>> queryData() {
        Function<List<String>, List<String>> fromCursorToList = new Function<List<String>, List<String>>() {
            @Override
            public List<String> apply(List<String> entityNames) throws Exception {
                names.addAll(entityNames);
                return names;
            }
        };
        Flowable<List<String>> flowable = observableQuery.map(fromCursorToList)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());

        return flowable;
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
