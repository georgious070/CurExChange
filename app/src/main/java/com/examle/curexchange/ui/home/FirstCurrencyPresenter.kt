package com.examle.curexchange.ui.home

import android.widget.EditText
import com.arellomobile.mvp.InjectViewState
import com.examle.curexchange.App
import com.examle.curexchange.App.Companion.app
import com.examle.curexchange.domain.CurrencyInteractor
import com.examle.curexchange.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class FirstCurrencyPresenter : BasePresenter<FirstCurrencyView>() {

    @Inject lateinit var currencyInteractor: CurrencyInteractor
    private var names: MutableList<String> = ArrayList()

    init {
        app.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProgress(true)
        currencyInteractor.loadData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    viewState.showData(list)
                    names.addAll(list)
                    loadProgress(false)
                }
    }

    fun onFloatButtonNextClicked(editText: EditText) {
        var value: Int = Integer.parseInt(editText.text.toString())
        viewState.handleFloatButton(names, value)
    }
}
