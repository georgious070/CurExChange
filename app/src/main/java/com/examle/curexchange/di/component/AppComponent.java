package com.examle.curexchange.di.component;

import com.examle.curexchange.App;
import com.examle.curexchange.di.module.AppModule;
import com.examle.curexchange.ui.home.FirstCurrencyPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(FirstCurrencyPresenter firstCurrencyPresenter);

    void inject(App app);
}
