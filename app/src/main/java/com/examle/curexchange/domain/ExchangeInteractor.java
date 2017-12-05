package com.examle.curexchange.domain;

import com.examle.curexchange.data.repository.ExchangeRepository;
import com.examle.curexchange.ui.result.ExchangeCallback;

import javax.inject.Inject;

public class ExchangeInteractor {

    private ExchangeRepository exchangeRepository;

    @Inject
    public ExchangeInteractor(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public void getResult(ExchangeCallback exchangeCallback, String firstName, String secondName, int value) {
        exchangeRepository.getResult(exchangeCallback, firstName, secondName, value);
    }
}
