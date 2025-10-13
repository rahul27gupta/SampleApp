package com.sampleapp

import android.app.Application
import com.sampleapp.di.component.AppComponent
import com.sampleapp.di.component.DaggerAppComponent
import com.sampleapp.di.module.ContextModule

class SampleApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}