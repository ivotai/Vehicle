package com.unicorn.vehicle.ui

import android.os.Bundle
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.Param
import com.unicorn.vehicle.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_car_usage_log_list.*

class CarUsageLogListAct : BaseAct() {

    override fun initViews() {
        toolbar.title = "车辆使用记录"
        val fra = CarUsageLogListFra()
        fra.arguments = Bundle().apply {
            putString(Param, carId)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fra)
            .commit()
    }

    private val carId by lazy { intent.getStringExtra(Param) }

    override val layoutId = R.layout.act_car_usage_log_list

}