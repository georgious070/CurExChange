package com.examle.curexchange.domain;

import com.examle.curexchange.data.repository.currency.CurrencyRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CurrencyInteractor {

    private CurrencyRepository currencyRepository;

    @Inject
    public CurrencyInteractor(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Flowable<List<String>> loadData() {
        return currencyRepository.getNames().observeOn(AndroidSchedulers.mainThread());
    }
}
