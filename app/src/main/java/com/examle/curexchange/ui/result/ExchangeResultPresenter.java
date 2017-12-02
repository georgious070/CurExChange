package com.examle.curexchange.ui.result;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;

import javax.inject.Inject;

@InjectViewState
public class ExchangeResultPresenter {

    @Inject
    CurrencyInteractor currencyInteractor;

    public ExchangeResultPresenter() {
        App.getApp().getAppComponent().inject(this);
    }
}
