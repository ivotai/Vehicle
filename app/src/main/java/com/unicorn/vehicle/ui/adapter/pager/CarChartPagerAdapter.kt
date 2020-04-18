package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.chart.Chart1NFra

class CarChartPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("总使用次数", "平均每日使用时长")
    }

    override fun getItem(position: Int) =
        if (position == 0) Chart1NFra() else Chart1NFra()

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}