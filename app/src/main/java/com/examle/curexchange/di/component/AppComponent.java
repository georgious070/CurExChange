package com.examle.curexchange.di.component;

import com.examle.curexchange.di.module.AppModule;
import com.examle.curexchange.ui.home.CurrencyPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(CurrencyPresenter currencyPresenter);
}
