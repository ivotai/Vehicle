package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.CarListFra
import com.unicorn.vehicle.ui.CarRequisitionFra
import com.unicorn.vehicle.ui.CarUsageLogListFra
import com.unicorn.vehicle.ui.ChartsFra

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("用车申请", "车辆状态", "车辆使用记录")
        // "数据统计"
    }

    override fun getItem(position: Int) = when (position) {
        0 -> CarRequisitionFra()
        1 -> CarListFra()
        else -> CarUsageLogListFra()
    }

    override fun getCount() = titles.size

}