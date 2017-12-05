package com.examle.curexchange.ui.home;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.App;
import com.examle.curexchange.domain.CurrencyInteractor;
import com.examle.curexchange.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FirstCurrencyPresenter extends BasePresenter<FirstCurrencyView> {

    private List<String> names;

    @Inject
    CurrencyInteractor currencyInteractor;
    private FirstCurrencyCallback firstCurrencyCallback = new FirstCurrencyCallback() {
        @Override
        public void onSuccess(final List<String> names) {
            getViewState().showData(names);
            setNames(names);
        }
    };

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
        currencyInteractor.loadData(firstCurrencyCallback);
    }

    public void buttonClicked(EditText editText) {
        int value = Integer.parseInt(editText.getText().toString());
        getViewState().handleFloatButton(getNames(), value);
    }
}
