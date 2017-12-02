package com.examle.curexchange.ui.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
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
    private static final String INTENT_KEY = "names";

    public static Intent getIntent(Context context, List<String> names) {
        Intent intent = new Intent(context, SecondCurrencyActivity.class);
        intent.putStringArrayListExtra(INTENT_KEY, (ArrayList<String>) names);
        return intent;
    }

    @ProvidePresenter
    SecondCurrencyPresenter providePresenter() {
        return new SecondCurrencyPresenter(getIntent().getStringArrayListExtra(INTENT_KEY));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_currency);

        secondCurrencyRecyclerView = findViewById(R.id.recycler_view_second_currency);
        currencyAdapter = new CurrencyAdapter(new ArrayList<String>(), this);
        secondCurrencyRecyclerView.setAdapter(currencyAdapter);
    }

    @Override
    public void showData(List<String> names) {
        currencyAdapter.setData(names);
    }

    @Override
    public void onClicked() {

    }
}
