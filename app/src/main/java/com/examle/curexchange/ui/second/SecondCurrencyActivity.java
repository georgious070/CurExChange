package com.examle.curexchange.ui.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.examle.curexchange.Constants;
import com.examle.curexchange.R;
import com.examle.curexchange.ui.base.BaseActivity;
import com.examle.curexchange.ui.adapter.CurrencyAdapter;
import com.examle.curexchange.ui.result.ExchangeResultActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondCurrencyActivity extends BaseActivity implements SecondCurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    private String secondName;

    @InjectPresenter
    SecondCurrencyPresenter secondCurrencyPresenter;
    private RecyclerView secondCurrencyRecyclerView;
    private CurrencyAdapter currencyAdapter;
    private FloatingActionButton floatingActionButton;

    public static Intent getIntent(Context context, List<String> names, String firstName) {
        Intent intent = new Intent(context, SecondCurrencyActivity.class);
        intent.putStringArrayListExtra(Constants.INTENT_KEY_NAMES, (ArrayList<String>) names);
        intent.putExtra(Constants.INTENT_KEY_FIRST_NAME, firstName);
        return intent;
    }

    @ProvidePresenter
    SecondCurrencyPresenter providePresenter() {
        return new SecondCurrencyPresenter(getIntent().getStringArrayListExtra(Constants.INTENT_KEY_NAMES),
                getIntent().getStringExtra(Constants.INTENT_KEY_FIRST_NAME));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_currency);

        secondCurrencyRecyclerView = findViewById(R.id.recycler_view_second_currency);
        currencyAdapter = new CurrencyAdapter(new ArrayList<String>(), this);
        secondCurrencyRecyclerView.setAdapter(currencyAdapter);

        floatingActionButton = findViewById(R.id.second_float_button_exchange);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondCurrencyPresenter.buttonClicked();
            }
        });
    }

    @Override
    public void showData(List<String> names) {
        currencyAdapter.setData(names);
    }

    @Override
    public void handleFloatButton(String firstName) {
        startActivity(ExchangeResultActivity.getIntent(SecondCurrencyActivity.this,
                firstName, secondName));
    }

    @Override
    public void onClicked(String secondName) {
        this.secondName = secondName;
    }
}
