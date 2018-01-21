package com.examle.curexchange.ui.result;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.ExchangeInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

@InjectViewState
public class ExchangeResultPresenter extends BasePresenter<ExchangeResultView> {

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
        exchangeInteractor.getResult(firstName, secondName, value)
                .subscribe(new DisposableObserver<Float>() {
                    @Override
                    public void onNext(Float result) {
                        getViewState().showData(result.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
