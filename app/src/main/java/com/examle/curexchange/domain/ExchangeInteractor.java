package com.examle.curexchange.domain;


import com.examle.curexchange.data.repository.ExchangeRepository;

import javax.inject.Inject;

public class ExchangeInteractor {

    private ExchangeRepository exchangeRepository;

    @Inject
    public ExchangeInteractor(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }
}
