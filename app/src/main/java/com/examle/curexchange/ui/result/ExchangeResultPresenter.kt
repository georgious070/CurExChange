package com.examle.curexchange.ui.result

import com.arellomobile.mvp.InjectViewState
import com.examle.curexchange.App.Companion.app
import com.examle.curexchange.domain.ExchangeInteractor
import com.examle.curexchange.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class ExchangeResultPresenter(var firstName: String, var secondName: String, var value: Int) : BasePresenter<ExchangeResultView>() {

    @Inject
    lateinit var exchangeInteractor: ExchangeInteractor
    private lateinit var subscription: Disposable

    init {
        app.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscription = exchangeInteractor
                .getResult(firstName, secondName, value)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> viewState.showData(result.toString()) }
    }

    override fun detachView(view: ExchangeResultView?) {
        super.detachView(view)
        subscription.dispose()
    }
}