package com.examle.curexchange.ui.result;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.ExchangeInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import javax.inject.Inject;

@InjectViewState
public class ExchangeResultPresenter extends BasePresenter<ExchangeResultView> {

    private ExchangeCallback exchangeCallback = new ExchangeCallback() {
        @Override
        public void onSuccess(int result) {
            getViewState().showData(String.valueOf(result));
        }

        @Override
        public void onFailure(String error) {
            getViewState().showData(error);
        }
    };

    @Inject
    ExchangeInteractor exchangeInteractor;
    private String firstName;
    private String secondName;
    private int value;

    public ExchangeResultPresenter(String firstName, String secondName, int value) {
        App.getApp().getAppComponent().inject(this);
        this.firstName = firstName;
        this.secondName = secondName;
        this.value = value;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        exchangeInteractor.getResult(exchangeCallback, firstName, secondName, value);
    }
}
