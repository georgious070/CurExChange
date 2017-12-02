package com.examle.curexchange.ui.second;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.examle.curexchange.R;
import com.examle.curexchange.ui.base.BaseActivity;
import com.examle.curexchange.ui.home.CurrencyAdapter;

import java.util.ArrayList;
import java.util.List;

public class SecondCurrencyActivity extends BaseActivity implements SecondCurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    @InjectPresenter
    SecondCurrencyPresenter secondCurrencyPresenter;
    private RecyclerView secondCurrencyRecyclerView;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        secondCurrencyRecyclerView = findViewById(R.id.recycler_view_second_currency);
        currencyAdapter = new CurrencyAdapter(new ArrayList<String>(), this);
        secondCurrencyRecyclerView.setAdapter(currencyAdapter);
    }

    @Override
    public void showData(List<String> names) {

    }

    @Override
    public void onClicked() {

    }
}
