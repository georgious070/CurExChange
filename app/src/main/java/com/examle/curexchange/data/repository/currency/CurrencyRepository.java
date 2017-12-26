package com.examle.curexchange.data.repository.currency;

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
        return Flowable.concat(insertToDbFromNetworkCall(), queryData())
                .subscribeOn(Schedulers.io());
    }

    private Flowable<List<String>> insertToDbFromNetworkCall() {
        return observableCryptoCode
                .subscribeOn(Schedulers.io())
                .doOnNext(cryptoCode -> {
                    rows.addAll(cryptoCode.getRows());
                    for (int i = 0; i < rows.size(); i++) {
                        currencyEntities.add(new CurrencyEntity(rows.get(i).getCode(), rows.get(i).getName()));
                    }
                    currencyDao.insertAll(currencyEntities);
                })
                .concatMap(cryptoCode -> Flowable.just(names))
                .observeOn(Schedulers.computation());
    }

    private Flowable<List<String>> queryData() {
        return observableQuery
                .doOnNext(entityNames -> names.addAll(entityNames))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }
}
