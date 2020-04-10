package com.unicorn.vehicle.ui

import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.data.model.event.CarRequisitionTotal
import com.unicorn.vehicle.ui.adapter.pager.CarRequisitionPagerAdapter
import com.unicorn.vehicle.ui.base.BaseFra
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fra_car_requisition.*

class CarRequisitionFra : BaseFra() {

    override fun initViews() {
        viewPaper.adapter = CarRequisitionPagerAdapter(childFragmentManager)
        viewPaper.offscreenPageLimit = CarRequisitionPagerAdapter.titles.size - 1
        tabs.setupWithViewPager(viewPaper)
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, CarRequisitionTotal::class.java, Consumer {
            // 只显示待处理的总数
            if (it.position == 1 || it.total == 0) return@Consumer
            val badge = tabs.getTabAt(it.position)!!.orCreateBadge
            badge.number = it.total
            badge.isVisible = true
        })
    }

    override val layoutId = R.layout.fra_car_requisition

}