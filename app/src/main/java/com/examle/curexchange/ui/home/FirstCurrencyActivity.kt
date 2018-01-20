package com.examle.curexchange.ui.home

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.widget.EditText
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
    private var firstCurrencyRecyclerView: RecyclerView? = null
    private var currencyAdapter: CurrencyAdapter? = null
    private var firstFloatingButton: FloatingActionButton? = null
    private var editText: EditText? = null
    private var firstName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_currency)

        firstCurrencyRecyclerView = findViewById(R.id.recycler_view_first_currency)
        currencyAdapter = CurrencyAdapter(ArrayList(), this)
        firstCurrencyRecyclerView!!.adapter = currencyAdapter

        editText = findViewById(R.id.edit_text)
        firstFloatingButton = findViewById(R.id.first_float_button_next)
        firstFloatingButton!!.setOnClickListener { firstCurrencyPresenter.onFloatButtonClick(editText) }
    }

    override fun showToast(error: String) {
        //no-op
    }

    override fun showData(names: MutableList<String>) {
        currencyAdapter!!.setData(names)
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
