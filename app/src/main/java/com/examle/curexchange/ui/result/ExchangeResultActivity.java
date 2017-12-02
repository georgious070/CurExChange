package com.examle.curexchange.ui.result;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.examle.curexchange.Constants;
import com.examle.curexchange.R;

public class ExchangeResultActivity extends AppCompatActivity implements ExchangeResultView {

    @InjectPresenter
    ExchangeResultPresenter exchangeResultPresenter;

    public static Intent getIntent(Context context, String firstName, String secondName) {
        Intent intent = new Intent(context, ExchangeResultActivity.class);
        intent.putExtra(Constants.INTENT_KEY_FIRST_NAME, firstName);
        intent.putExtra(Constants.INTENT_KEY_SECOND_NAME, secondName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_result);
    }

    @Override
    public void showData(String result) {

    }
}
