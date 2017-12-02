package com.examle.curexchange.ui.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.examle.curexchange.App;
import com.examle.curexchange.R;
import com.examle.curexchange.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.Provides;

public class CurrencyActivity extends BaseActivity implements CurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    @InjectPresenter
    CurrencyPresenter currencyPresenter;
    private RecyclerView firstCurrencyRecyclerView;
    private CurrencyAdapter currencyAdapter;
    private FloatingActionButton firstButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        firstCurrencyRecyclerView = findViewById(R.id.recycler_view_first_currency);
        currencyAdapter = new CurrencyAdapter(new ArrayList<String>(), this);
        firstCurrencyRecyclerView.setAdapter(currencyAdapter);

        firstButton = findViewById(R.id.first_float_button);
    }

    @Override
    public void onClicked() {

    }

    @Override
    public void showData(List<String> name) {
        currencyAdapter.setData(name);
    }

    @Override
    public void handleFloatButton() {
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getApp(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
