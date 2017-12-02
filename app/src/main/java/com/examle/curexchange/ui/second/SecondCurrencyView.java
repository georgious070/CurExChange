package com.examle.curexchange.ui.second;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.examle.curexchange.ui.base.BaseView;

import java.util.List;

@StateStrategyType(AddToEndStrategy.class)
public interface SecondCurrencyView extends BaseView {

    void showData(List<String> names);

    void handleFloatButton(String firstName);
}
