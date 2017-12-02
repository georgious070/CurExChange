package com.examle.curexchange.data.repository;


import com.examle.curexchange.ui.result.ExchangeCallback;

import javax.inject.Inject;

public class ExchangeRepository {

    @Inject
    public ExchangeRepository() {
    }

    void loadExchangeValue(ExchangeRecieveCallback exchangeRecieveCallback){

    }

    void getResult(final ExchangeCallback exchangeCallback){
        loadExchangeValue(new ExchangeRecieveCallback() {
            @Override
            public void onSuccess() {
            }
        });
    }
}
