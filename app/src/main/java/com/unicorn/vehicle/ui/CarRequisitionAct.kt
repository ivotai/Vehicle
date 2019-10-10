package com.unicorn.vehicle.ui

import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.data.model.event.CarRequisitionTotal
import com.unicorn.vehicle.ui.adapter.pager.CarRequisitionPagerAdapter
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_car_requisition.*

class CarRequisitionAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("用车申请")
        viewPaper.adapter = CarRequisitionPagerAdapter(supportFragmentManager)
        viewPaper.offscreenPageLimit = CarRequisitionPagerAdapter.titles.size - 1
        tabs.setupWithViewPager(viewPaper)
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, CarRequisitionTotal::class.java, Consumer {
            val pos = it.carRequisitionState
            val badge = tabs.getTabAt(pos)!!.orCreateBadge
            badge.isVisible = true
            badge.number = it.total
        })
    }

    override val layoutId = R.layout.act_car_requisition

}