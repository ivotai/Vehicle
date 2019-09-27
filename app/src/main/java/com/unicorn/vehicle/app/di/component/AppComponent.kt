package com.unicorn.vehicle.app.di.component

import com.unicorn.vehicle.app.di.module.ApiModule
import com.unicorn.vehicle.app.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApiModule::class])
interface AppComponent {

//    fun api(): SimpleApi

}