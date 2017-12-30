package com.examle.curexchange.domain;

import com.examle.curexchange.data.repository.exchange.ExchangeRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ExchangeInteractor {

    private ExchangeRepository exchangeRepository;

    @Inject
    public ExchangeInteractor(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public Observable<Float> getResult(String firstName, String secondName, int value) {
        return exchangeRepository.getResult(firstName, secondName, value);
    }
}
