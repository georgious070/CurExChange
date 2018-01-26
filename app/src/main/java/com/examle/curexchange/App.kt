package com.examle.curexchange

import android.app.Application
import com.examle.curexchange.di.component.AppComponent
import com.examle.curexchange.di.component.DaggerAppComponent
import com.examle.curexchange.di.module.AppModule
import com.squareup.leakcanary.LeakCanary

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        lateinit var app: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
        app = this
        appComponent.inject(this)
    }
}