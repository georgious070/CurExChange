package com.examle.curexchange.ui.home;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class CurrencyPresenter extends BasePresenter<CurrencyView> {

    @Inject
    CurrencyInteractor currencyInteractor;
    private CurrencyCallback currencyCallback = new CurrencyCallback() {
        @Override
        public void onSuccess(List<String> names) {
            getViewState().showData(names);
        }
    };

    public CurrencyPresenter() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        currencyInteractor.loadData(currencyCallback);
    }
}
