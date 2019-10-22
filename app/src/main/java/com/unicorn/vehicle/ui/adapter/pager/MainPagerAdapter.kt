package com.unicorn.vehicle.ui.adapter.pager

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.ui.CarRequisitionFra

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("用车申请", "车辆状态")
    }

    override fun getItem(position: Int) = CarRequisitionFra().apply {
        arguments = Bundle().apply {
            putInt(Key.Position, position)
        }
    }

    override fun getCount() = titles.size

//    override fun getPageTitle(position: Int) = titles[position]

}