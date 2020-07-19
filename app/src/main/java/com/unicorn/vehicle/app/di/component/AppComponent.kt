package com.unicorn.vehicle.app.di.component

import android.content.Context
import com.google.gson.Gson
import com.unicorn.vehicle.app.di.module.ApiModule
import com.unicorn.vehicle.app.di.module.BasicModule
import com.unicorn.vehicle.app.di.module.NetworkModule
import com.unicorn.vehicle.data.api.SimpleApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BasicModule::class, NetworkModule::class, ApiModule::class])
interface AppComponent {

    fun simpleApi(): SimpleApi

    fun context(): Context

    fun gson(): Gson

}