package com.examle.curexchange.ui.second

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.examle.curexchange.ui.base.BaseView

@StateStrategyType(AddToEndStrategy::class)
interface SecondCurrencyView : BaseView {

    fun showData(names: MutableList<String>)

    fun handleFloatButton(firstName: String, value: Int)
}