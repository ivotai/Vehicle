package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.Chart1Fra
import com.unicorn.vehicle.ui.Chart2Fra

class ChartsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("按车辆", "按人员")
    }

    override fun getItem(position: Int) =
        if (position == 0) Chart1Fra() else Chart2Fra()

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}