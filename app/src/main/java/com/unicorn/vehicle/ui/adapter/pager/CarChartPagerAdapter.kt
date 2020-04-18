package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.chart.Chart1Fra
import com.unicorn.vehicle.ui.chart.Chart2Fra

class CarChartPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("总使用次数", "平均每日使用时长")
    }

    override fun getItem(position: Int) =
        if (position == 0) Chart1Fra() else Chart2Fra()

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}