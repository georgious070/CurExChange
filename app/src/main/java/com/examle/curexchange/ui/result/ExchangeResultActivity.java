package com.examle.curexchange.ui.result;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.examle.curexchange.Constants;
import com.examle.curexchange.R;
import com.examle.curexchange.ui.base.BaseActivity;

public class ExchangeResultActivity extends BaseActivity implements ExchangeResultView {

    @InjectPresenter
    ExchangeResultPresenter exchangeResultPresenter;
    private TextView textView;

    public static Intent getIntent(Context context, String firstName, String secondName) {
        Intent intent = new Intent(context, ExchangeResultActivity.class);
        intent.putExtra(Constants.INTENT_KEY_FIRST_NAME, firstName);
        intent.putExtra(Constants.INTENT_KEY_SECOND_NAME, secondName);
        return intent;
    }

    @ProvidePresenter
    ExchangeResultPresenter providePresenter() {
        return new ExchangeResultPresenter(getIntent().getStringExtra(Constants.INTENT_KEY_FIRST_NAME),
                getIntent().getStringExtra(Constants.INTENT_KEY_SECOND_NAME));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_result);

        textView = findViewById(R.id.result_of_exchange);
    }

    @Override
    public void showData(String result) {

    }
}
