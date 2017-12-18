package com.examle.curexchange.domain;

import com.examle.curexchange.data.repository.currency.CurrencyRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CurrencyInteractor {

    private CurrencyRepository currencyRepository;

    @Inject
    public CurrencyInteractor(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Observable<List<String>> loadData() {
        return currencyRepository.getNames();
    }
}
