package com.unicorn.vehicle.ui.adapter.pager

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.app.Position
import com.unicorn.vehicle.ui.CarRequisitionListFra

class CarRequisitionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("待处理", "已处理")
    }

    override fun getItem(position: Int) = CarRequisitionListFra().apply {
        arguments = Bundle().apply {
            putInt(Position, position)
        }
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}