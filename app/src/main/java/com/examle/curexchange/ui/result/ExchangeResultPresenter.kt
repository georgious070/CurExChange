package com.examle.curexchange.ui.result

import com.arellomobile.mvp.InjectViewState
import com.examle.curexchange.App
import com.examle.curexchange.domain.ExchangeInteractor
import com.examle.curexchange.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ExchangeResultPresenter(var firstName: String, var secondName: String, var value: Int) : BasePresenter<ExchangeResultView>() {

    @Inject
    lateinit var exchangeInteractor: ExchangeInteractor

    init {
        App.getApp().appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        exchangeInteractor
                .getResult(firstName, secondName, value)
                .subscribe { n -> viewState.showData(n.toString()) }
    }
}