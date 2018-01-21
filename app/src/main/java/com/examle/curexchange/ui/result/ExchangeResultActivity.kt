package com.examle.curexchange.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.examle.curexchange.Constants
import com.examle.curexchange.R
import com.examle.curexchange.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_exchange_result.*


class ExchangeResultActivity : BaseActivity(), ExchangeResultView {

    @InjectPresenter
    lateinit var exchangeResultPresenter: ExchangeResultPresenter

    companion object {
        fun getIntent(context: Context, firstName: String, secondName: String, value: Int): Intent {
            val intent: Intent = Intent(context, ExchangeResultActivity::class.java)
            intent.putExtra(Constants.INTENT_KEY_FIRST_NAME, firstName)
            intent.putExtra(Constants.INTENT_KEY_SECOND_NAME, secondName)
            intent.putExtra(Constants.INTENT_KEY_VALUE, value)
            return intent
        }
    }

    @ProvidePresenter
    fun providePresenter(): ExchangeResultPresenter {
        return ExchangeResultPresenter(intent.getStringExtra(Constants.INTENT_KEY_FIRST_NAME),
                intent.getStringExtra(Constants.INTENT_KEY_SECOND_NAME),
                intent.getIntExtra(Constants.INTENT_KEY_VALUE, 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_result)
    }

    override fun showData(result: String) {
        result_of_exchange.text = result
    }
}