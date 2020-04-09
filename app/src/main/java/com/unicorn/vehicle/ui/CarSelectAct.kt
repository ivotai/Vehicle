package com.unicorn.vehicle.ui

import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.CarRequisition
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.ui.adapter.pager.CarSelectPagerAdapter
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_car_select.*

class CarSelectAct : BaseAct() {

    override val layoutId = R.layout.act_car_select

    override fun initViews() {
        titleBar.setTitle("选择车辆")
        viewPaper.adapter = CarSelectPagerAdapter(supportFragmentManager, carRequisition)
        viewPaper.offscreenPageLimit = CarSelectPagerAdapter.titles.size - 1
        tabs.setupWithViewPager(viewPaper)
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, Car::class.java, Consumer {
            finish()
        })
    }

    private val carRequisition by lazy { intent.getSerializableExtra(CarRequisition) as CarRequisition }

}