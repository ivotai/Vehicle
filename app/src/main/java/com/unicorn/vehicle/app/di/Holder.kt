package com.unicorn.vehicle.app.di

import android.app.Application
import com.unicorn.vehicle.app.di.component.AppComponent
import com.unicorn.vehicle.app.di.component.DaggerAppComponent
import com.unicorn.vehicle.app.di.module.BasicModule

object Holder {

    lateinit var appComponent: AppComponent

    fun init(application: Application) {
        val basicModule = BasicModule(application.applicationContext)
        appComponent = DaggerAppComponent.builder().basicModule(basicModule).build()
    }

}