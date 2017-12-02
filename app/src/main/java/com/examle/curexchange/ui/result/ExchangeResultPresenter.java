package com.examle.curexchange.ui.result;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import javax.inject.Inject;

@InjectViewState
public class ExchangeResultPresenter extends BasePresenter<ExchangeResultView>{

    @Inject
    CurrencyInteractor currencyInteractor;

    public ExchangeResultPresenter() {
        App.getApp().getAppComponent().inject(this);
    }
}
