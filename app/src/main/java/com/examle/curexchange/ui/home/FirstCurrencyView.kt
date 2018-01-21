package com.examle.curexchange.ui.home

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.examle.curexchange.ui.base.BaseView

@StateStrategyType(AddToEndStrategy::class)
interface FirstCurrencyView : BaseView{

    fun showData(names: MutableList<String>)

    fun handleFloatButton(names: MutableList<String>, value: Int)
}