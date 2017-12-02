package com.examle.curexchange.domain;

import com.examle.curexchange.data.repository.CurrencyRepository;
import com.examle.curexchange.ui.home.CurrencyCallback;

import javax.inject.Inject;

public class CurrencyInteractor {

    private CurrencyRepository currencyRepository;

    @Inject
    public CurrencyInteractor(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void loadData(CurrencyCallback currencyCallback){
        currencyRepository.getNames(currencyCallback);
    }
}
