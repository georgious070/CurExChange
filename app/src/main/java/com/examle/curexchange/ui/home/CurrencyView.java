package com.examle.curexchange.ui.home;

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.examle.curexchange.ui.base.BaseView;

import java.util.List;

@StateStrategyType(AddToEndStrategy.class)
public interface CurrencyView extends BaseView {

    void showData(List<String> name);
}
