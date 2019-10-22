package com.unicorn.vehicle.app.di

import android.app.Application
import com.unicorn.vehicle.app.di.component.AppComponent
import com.unicorn.vehicle.app.di.component.DaggerAppComponent
import com.unicorn.vehicle.app.di.module.BasicModule

object ComponentHolder {

    lateinit var appComponent: AppComponent

    fun init(application: Application) {
        appComponent =
            DaggerAppComponent.builder().basicModule(BasicModule(application.applicationContext))
                .build()
    }

}