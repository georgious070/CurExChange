package com.examle.curexchange.ui.result

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.examle.curexchange.ui.base.BaseView

@StateStrategyType(AddToEndStrategy::class)
interface ExchangeResultView : BaseView {

    fun showData(result: String)
}