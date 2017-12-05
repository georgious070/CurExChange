package com.examle.curexchange.ui.second;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.ui.base.BasePresenter;

import java.util.List;

@InjectViewState
public class SecondCurrencyPresenter extends BasePresenter<SecondCurrencyView> {

    private List<String> names;
    private String firstName;
    private int value;

    public SecondCurrencyPresenter(List<String> names, String firstName, int value) {
        this.names = names;
        this.firstName = firstName;
        this.value = value;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showData(names);
    }

    void buttonClicked() {
        getViewState().handleFloatButton(firstName, value);
    }
}
