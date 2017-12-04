package com.examle.curexchange.ui.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.examle.curexchange.R;
import com.examle.curexchange.ui.adapter.CurrencyAdapter;
import com.examle.curexchange.ui.base.BaseActivity;
import com.examle.curexchange.ui.second.SecondCurrencyActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstCurrencyActivity extends BaseActivity implements FirstCurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    @InjectPresenter
    FirstCurrencyPresenter firstCurrencyPresenter;
    private RecyclerView firstCurrencyRecyclerView;
    private CurrencyAdapter currencyAdapter;
    private FloatingActionButton firstButton;
    private EditText editText;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_currency);

        firstCurrencyRecyclerView = findViewById(R.id.recycler_view_first_currency);
        currencyAdapter = new CurrencyAdapter(new ArrayList<String>(), this);
        firstCurrencyRecyclerView.setAdapter(currencyAdapter);

        editText = findViewById(R.id.edit_text);
        firstButton = findViewById(R.id.first_float_button_next);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstCurrencyPresenter.buttonClicked(editText);
            }
        });
    }

    @Override
    public void onClicked(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void showData(List<String> name) {
        currencyAdapter.setData(name);
    }

    @Override
    public void handleFloatButton(List<String> names, int value) {
        startActivity(SecondCurrencyActivity.getIntent(this, names, firstName, value));
    }
}
