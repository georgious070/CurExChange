package com.examle.curexchange.ui.home

import android.widget.EditText
import com.arellomobile.mvp.InjectViewState
import com.examle.curexchange.App
import com.examle.curexchange.domain.CurrencyInteractor
import com.examle.curexchange.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class FirstCurrencyPresenter : BasePresenter<FirstCurrencyView>() {

    @Inject lateinit var currencyInteractor: CurrencyInteractor
    private var names: MutableList<String>

    init {
        names = ArrayList()
        App.getApp().appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProgress(true)
        currencyInteractor.loadData().subscribe(
                { n ->
                    viewState.showData(n)
                    names = n
                    loadProgress(false)
                })
    }

    fun onFloatButtonClick(editText: EditText) {
        var value: Int = Integer.parseInt(editText.text.toString())
        viewState.handleFloatButton(names, value)
    }
}
