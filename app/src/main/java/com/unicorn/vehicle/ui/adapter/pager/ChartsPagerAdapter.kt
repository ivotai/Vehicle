package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.BarChartActivityMultiDataset
import com.unicorn.vehicle.ui.Chart1Fra

class ChartsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("车辆申请次数")
    }

    override fun getItem(position: Int) =
        if (position == 0) Chart1Fra() else BarChartActivityMultiDataset()

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}