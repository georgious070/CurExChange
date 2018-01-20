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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository {

    private List<String> names;
    private final List<Row> rows;
    private List<CurrencyEntity> currencyEntities;
    private CurrencyDao currencyDao;
    private final Flowable<CryptoCode> flowableApiCryptoCode;
    private final Flowable<List<String>> flowableDbQuery;
    private boolean isDbEmpty = true;

    @Inject
    public CurrencyRepository(ApiCryptoCode apiCryptoCode, CurrencyDao currencyDao) {
        this.currencyEntities = new ArrayList<>();
        this.names = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.currencyDao = currencyDao;
        this.flowableApiCryptoCode = apiCryptoCode.getCryptoCodes();
        this.flowableDbQuery = currencyDao.queryCryptoNames();
    }

    public Flowable<List<String>> getNames() {
        return queryData();
    }

    private Flowable<List<String>> queryData() {
        return flowableApiCryptoCode
                .subscribeOn(Schedulers.io())
                .flatMap(code -> {
                   if (isDbEmpty()) {
                        insertToDbFromNetwork(code);
                    }
                    return flowableDbQuery
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(entityNames -> names.addAll(entityNames));
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void insertToDbFromNetwork(CryptoCode code) {
        rows.addAll(code.getRows());
        for (int i = 0; i < rows.size(); i++) {
            currencyEntities.add(new CurrencyEntity(rows.get(i).getCode(), rows.get(i).getName()));
        }
        currencyDao.insertAll(currencyEntities);
    }

    private boolean isDbEmpty() {
        currencyDao.queryOneLine()
                .subscribe(new DisposableMaybeObserver<CurrencyEntity>() {
                    @Override
                    public void onSuccess(CurrencyEntity currencyEntity) {
                        if (currencyEntity != null) {
                            isDbEmpty = false;
                        } else {
                            isDbEmpty = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return isDbEmpty;
    }
}
