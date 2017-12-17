package com.examle.curexchange.domain;

import com.examle.curexchange.data.repository.currency.CurrencyRepository;
import com.examle.curexchange.ui.home.FirstCurrencyCallback;

import javax.inject.Inject;

public class CurrencyInteractor {

    private CurrencyRepository currencyRepository;

    @Inject
    public CurrencyInteractor(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void loadData(FirstCurrencyCallback firstCurrencyCallback) {
        currencyRepository.getNames(firstCurrencyCallback);
    }
}
