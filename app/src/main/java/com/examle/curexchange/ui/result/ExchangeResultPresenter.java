package com.examle.curexchange.ui.result;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.domain.ExchangeInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import javax.inject.Inject;

@InjectViewState
public class ExchangeResultPresenter extends BasePresenter<ExchangeResultView> {

    private ExchangeCallback exchangeCallback = new ExchangeCallback() {
        @Override
        public void onSuccess() {

        }
    };
    @Inject
    ExchangeInteractor exchangeInteractor;
    private String firstName;
    private String secondName;

    public ExchangeResultPresenter(String firstName, String secondName) {
        App.getApp().getAppComponent().inject(this);
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        exchangeInteractor.getResult(exchangeCallback, firstName, secondName);
        getViewState().showData(firstName);
    }
}
