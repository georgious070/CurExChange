package com.examle.curexchange.ui.home

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.examle.curexchange.R
import com.examle.curexchange.ui.adapter.CurrencyAdapter
import com.examle.curexchange.ui.base.BaseActivity
import com.examle.curexchange.ui.second.SecondCurrencyActivity
import kotlinx.android.synthetic.main.activity_first_currency.*

class FirstCurrencyActivity : BaseActivity(),
        FirstCurrencyView,
        CurrencyAdapter.OnRecyclerItemClickedListener {

    @InjectPresenter lateinit var firstCurrencyPresenter: FirstCurrencyPresenter
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var firstName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_currency)

        currencyAdapter = CurrencyAdapter(ArrayList(), this)
        recycler_view_first_currency.adapter = currencyAdapter

        first_float_button_next.setOnClickListener { firstCurrencyPresenter.onFloatButtonClick(edit_text) }
    }

    override fun showData(names: MutableList<String>) {
        currencyAdapter.setData(names)
    }

    override fun handleFloatButton(names: MutableList<String>, value: Int) {
        startActivity(SecondCurrencyActivity
                .getIntent(this@FirstCurrencyActivity,
                        names,
                        firstName,
                        value))
    }

    override fun onClicked(name: String) {
        firstName = name
    }
}
