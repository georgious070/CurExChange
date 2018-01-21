package com.examle.curexchange.ui.second

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.examle.curexchange.Constants
import com.examle.curexchange.R
import com.examle.curexchange.ui.adapter.CurrencyAdapter
import com.examle.curexchange.ui.base.BaseActivity
import com.examle.curexchange.ui.result.ExchangeResultActivity
import kotlinx.android.synthetic.main.activity_second_currency.*
import kotlin.collections.ArrayList

class SecondCurrencyActivity : BaseActivity(),
        SecondCurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    @InjectPresenter
    lateinit var secondCurrencyPresenter: SecondCurrencyPresenter
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var secondName: String

    companion object {
        fun getIntent(context: Context, names: List<String>, firstName: String, value: Int): Intent {
            val intent = Intent(context, SecondCurrencyActivity::class.java)
            intent.putStringArrayListExtra(Constants.INTENT_KEY_NAMES, names as ArrayList<String>)
            intent.putExtra(Constants.INTENT_KEY_FIRST_NAME, firstName)
            intent.putExtra(Constants.INTENT_KEY_VALUE, value)
            return intent
        }
    }

    @ProvidePresenter
    fun providePresenter(): SecondCurrencyPresenter {
        return SecondCurrencyPresenter(intent.getStringArrayListExtra(Constants.INTENT_KEY_NAMES),
                intent.getStringExtra(Constants.INTENT_KEY_FIRST_NAME),
                intent.getIntExtra(Constants.INTENT_KEY_VALUE, 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_currency)

        currencyAdapter = CurrencyAdapter(ArrayList(), this)
        recycler_view_second_currency.adapter = currencyAdapter

        second_float_button_exchange
                .setOnClickListener { secondCurrencyPresenter.onFloatButtonExchangeClicked() }
    }

    override fun showData(names: MutableList<String>) {
        currencyAdapter.setData(names)
    }

    override fun handleFloatButton(firstName: String, value: Int) {
        startActivity(ExchangeResultActivity.getIntent(this, firstName, secondName, value))
    }

    override fun onClicked(name: String) {
        secondName = name
    }
}