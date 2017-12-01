package com.examle.curexchange.ui.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.examle.curexchange.R;
import com.examle.curexchange.ui.base.BaseActivity;

import java.util.ArrayList;

public class CurrencyActivity extends BaseActivity implements CurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    @InjectPresenter
    CurrencyPresenter currencyPresenter;
    private RecyclerView firstCurrencyRecyclerView;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        firstCurrencyRecyclerView = findViewById(R.id.recycler_view_first_currency);
        currencyAdapter = new CurrencyAdapter(new ArrayList<String>(), this);
        firstCurrencyRecyclerView.setAdapter(currencyAdapter);
    }

    @Override
    public void onClicked() {

    }
}
