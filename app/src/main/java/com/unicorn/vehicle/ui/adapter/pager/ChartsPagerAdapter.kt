package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.Chart1Fra

class ChartsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("车辆申请次数", "车辆使用时间", "人员申请次数", "人员使用时间")
    }

    override fun getItem(position: Int) = Chart1Fra()

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}