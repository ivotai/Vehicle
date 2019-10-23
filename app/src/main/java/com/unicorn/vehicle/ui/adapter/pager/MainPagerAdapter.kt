package com.unicorn.vehicle.ui.adapter.pager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.ui.CarListFra
import com.unicorn.vehicle.ui.CarRequisitionFra

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("用车申请", "车辆状态")
    }

    override fun getItem(position: Int) = if (position == 0) CarRequisitionFra() else CarListFra()

    override fun getCount() = titles.size

}