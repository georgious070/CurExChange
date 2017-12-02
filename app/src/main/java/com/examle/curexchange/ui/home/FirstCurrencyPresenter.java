package com.examle.curexchange.ui.home;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FirstCurrencyPresenter extends BasePresenter<FirstCurrencyView> {

    @Inject
    CurrencyInteractor currencyInteractor;
    private FirstCurrencyCallback firstCurrencyCallback = new FirstCurrencyCallback() {
        @Override
        public void onSuccess(List<String> names) {
            getViewState().showData(names);
        }
    };

    public FirstCurrencyPresenter() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        currencyInteractor.loadData(firstCurrencyCallback);
        getViewState().handleFloatButton();
    }
}
