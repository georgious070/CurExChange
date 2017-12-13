package com.examle.curexchange.ui.home;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FirstCurrencyPresenter extends BasePresenter<FirstCurrencyView> {

    private List<String> names;

    @Inject
    CurrencyInteractor currencyInteractor;
    private Subscription subscription;

    public FirstCurrencyPresenter() {
        names = new ArrayList<>();
        App.getApp().getAppComponent().inject(this);
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadProgress(true);
        //subscription = currencyInteractor.loadData();
    }

    public void buttonClicked(EditText editText) {
        int value = Integer.parseInt(editText.getText().toString());
        getViewState().handleFloatButton(getNames(), value);
    }

    @Override
    public void loadProgress(boolean isProgress) {
        super.loadProgress(isProgress);
    }
}
