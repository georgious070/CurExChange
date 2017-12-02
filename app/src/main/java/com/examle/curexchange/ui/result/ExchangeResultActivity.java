package com.examle.curexchange.ui.result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.examle.curexchange.R;

public class ExchangeResultActivity extends AppCompatActivity implements ExchangeResultView {

    @InjectPresenter
    ExchangeResultPresenter exchangeResultPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_result);
    }

    @Override
    public void showData(String result) {

    }
}
