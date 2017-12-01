package com.examle.curexchange;

import android.app.Application;

import com.examle.curexchange.di.component.AppComponent;
import com.examle.curexchange.di.component.DaggerAppComponent;
import com.examle.curexchange.di.module.AppModule;

public class App extends Application {

    private AppComponent appComponent;
    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public AppComponent getAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        return appComponent;
    }
}
