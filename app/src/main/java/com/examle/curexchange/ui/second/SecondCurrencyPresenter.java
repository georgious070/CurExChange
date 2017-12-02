package com.examle.curexchange.ui.second;

import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.ui.base.BasePresenter;

import java.util.List;

@InjectViewState
public class SecondCurrencyPresenter extends BasePresenter<SecondCurrencyView> {

    private List<String> names;

    public SecondCurrencyPresenter(List<String> names) {
        this.names = names;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showData(names);
    }
}
