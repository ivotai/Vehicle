package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.Chart1Fra
import com.unicorn.vehicle.ui.Chart3Fra

class ChartsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("申请次数", "混合")
    }

    override fun getItem(position: Int) = if (position == 0) Chart1Fra() else Chart3Fra()

    override fun getCount() = titles.size

}