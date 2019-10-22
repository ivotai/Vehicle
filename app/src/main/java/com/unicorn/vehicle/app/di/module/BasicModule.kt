package com.unicorn.vehicle.app.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BasicModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideGson(): Gson =
        GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()// 2019-10-09T15:40:29.103

}