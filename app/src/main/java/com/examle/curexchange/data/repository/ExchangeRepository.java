package com.examle.curexchange.data.repository;


import android.content.AsyncQueryHandler;

import com.examle.curexchange.ui.result.ExchangeCallback;

import javax.inject.Inject;

public class ExchangeRepository {


    @Inject
    public ExchangeRepository() {
    }

    void loadExchangeValue(){

    }

    void getResult(final ExchangeCallback exchangeCallback){
        queryCodesFromDB(new QueryCodeCallback() {
            @Override
            public void onSuccess(String code) {

            }
        });
    }

    void queryCodesFromDB(QueryCodeCallback queryCodeCallback){
    }
}
