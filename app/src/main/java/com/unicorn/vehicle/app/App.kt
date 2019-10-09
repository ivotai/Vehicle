package com.unicorn.vehicle.app

import androidx.multidex.MultiDexApplication
import com.unicorn.vehicle.app.helper.DictHelper

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        doMyWork()
    }

    private fun doMyWork() {
        DictHelper.initDict()
    }

}