package com.examle.curexchange.di.component;

import com.examle.curexchange.App;
import com.examle.curexchange.di.module.AppModule;
import com.examle.curexchange.ui.home.FirstCurrencyPresenter;
import com.examle.curexchange.ui.result.ExchangeResultPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(FirstCurrencyPresenter firstCurrencyPresenter);

    void inject(ExchangeResultPresenter exchangeResultPresenter);

    void inject(App app);
}
