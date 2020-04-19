package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.chart.CarChartFra
import com.unicorn.vehicle.ui.chart.UserChartFra

class ChartsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("车辆", "人员")
    }

    override fun getItem(position: Int) =
        if (position == 0) CarChartFra() else UserChartFra()

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}