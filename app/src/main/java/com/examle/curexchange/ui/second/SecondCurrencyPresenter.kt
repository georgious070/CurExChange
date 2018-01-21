package com.examle.curexchange.ui.second

import com.arellomobile.mvp.InjectViewState
import com.examle.curexchange.ui.base.BasePresenter

@InjectViewState
class SecondCurrencyPresenter(var names: List<String>, var firstName: String, var value: Int) :
        BasePresenter<SecondCurrencyView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showData(names)
    }

    fun onFloatButtonExchangeClicked() {
        viewState.handleFloatButton(firstName, value)
    }
}