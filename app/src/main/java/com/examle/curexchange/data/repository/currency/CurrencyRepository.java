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

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository {

    private List<String> names;
    private final List<Row> rows;
    private List<CurrencyEntity> currencyEntities;
    private CurrencyDao currencyDao;
    private final Flowable<CryptoCode> observableCryptoCode;
    private final Flowable<List<String>> observableQuery;

    @Inject
    public CurrencyRepository(ApiCryptoCode apiCryptoCode, CurrencyDao currencyDao) {
        this.currencyEntities = new ArrayList<>();
        this.names = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.currencyDao = currencyDao;
        this.observableCryptoCode = apiCryptoCode.getCryptoCodes();
        this.observableQuery = currencyDao.queryCryptoNames();

    }

    public Flowable<List<String>> getNames() {
        Flowable<List<String>> ob = Flowable.concat(insertToDbFromNetworkCall(), queryData())
                .first()
                .subscribeOn(Schedulers.io());
        return ob;
    }

    private Flowable<List<String>> insertToDbFromNetworkCall() {
        return observableCryptoCode
                .subscribeOn(Schedulers.io())
                .concatMap(cryptoCode -> {
                    rows.addAll(cryptoCode.getRows());
                    for (int i = 0; i < rows.size(); i++) {
                        currencyEntities.add(new CurrencyEntity(rows.get(i).getCode(), rows.get(i).getName()));
                    }
                    currencyDao.insertAll(currencyEntities);
                    return Flowable.just(names);
                }).observeOn(Schedulers.computation());
    }

    private Flowable<List<String>> queryData() {
        Flowable<List<String>> flowable = observableQuery
                .doOnNext(entityNames -> names.addAll(entityNames))
                .filter(strings -> !strings.isEmpty())
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
