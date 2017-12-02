package com.examle.curexchange.ui.second;


import com.arellomobile.mvp.InjectViewState;
import com.examle.curexchange.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class SecondCurrencyPresenter extends BasePresenter<SecondCurrencyView> {

    private List<String> names = new ArrayList<>();

    public SecondCurrencyPresenter(List<String> names) {
        this.names.clear();
        this.names.addAll(names);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showData(names);
    }
}
