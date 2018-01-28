package com.examle.curexchange.ui.result

import com.arellomobile.mvp.InjectViewState
import com.examle.curexchange.App.Companion.app
import com.examle.curexchange.domain.ExchangeInteractor
import com.examle.curexchange.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.reactivestreams.Subscriber
import javax.inject.Inject

@InjectViewState
class ExchangeResultPresenter(var firstName: String, var secondName: String, var value: Int) : BasePresenter<ExchangeResultView>() {

    @Inject
    lateinit var exchangeInteractor: ExchangeInteractor

    init {
        app.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        exchangeInteractor
                .getResult(firstName, secondName, value)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> viewState.showData(result.toString()) }
    }
}