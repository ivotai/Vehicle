package com.unicorn.vehicle.app

import androidx.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import com.unicorn.vehicle.app.helper.DictHelper

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        doMyWork()
    }

    private fun doMyWork() {
        DictHelper.initDict()
        JPushInterface.init(this)
//        JPushInterface.setDebugMode(true)
    }

}