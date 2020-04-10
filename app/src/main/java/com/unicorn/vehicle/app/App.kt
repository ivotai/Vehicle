package com.unicorn.vehicle.app

import androidx.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import com.unicorn.vehicle.app.di.Holder

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        Holder.init(this)
        JPushInterface.init(this)
    }

}