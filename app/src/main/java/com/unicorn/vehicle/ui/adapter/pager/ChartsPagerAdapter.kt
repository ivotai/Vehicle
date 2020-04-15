package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.Chart1Fra

class ChartsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("申请次数", "使用时间", "混合")
    }

    override fun getItem(position: Int): Fragment {
        return Chart1Fra()
    }

    override fun getCount() = titles.size

}