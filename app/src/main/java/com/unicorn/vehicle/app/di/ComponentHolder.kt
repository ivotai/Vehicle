package com.unicorn.vehicle.app.di

import com.unicorn.vehicle.app.di.component.AppComponent
import com.unicorn.vehicle.app.di.component.DaggerAppComponent

object ComponentHolder {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

}