package com.examle.curexchange.di.component

import com.examle.curexchange.App
import com.examle.curexchange.di.module.AppModule
import com.examle.curexchange.ui.home.FirstCurrencyPresenter
import com.examle.curexchange.ui.result.ExchangeResultPresenter
import com.examle.curexchange.ui.second.SecondCurrencyPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(firstCurrencyPresenter: FirstCurrencyPresenter)

    fun inject(exchangeResultPresenter: ExchangeResultPresenter)

    fun inject(app:App)
}