package com.unicorn.vehicle.ui.adapter.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.app.Param
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.ui.CarSelectFra
import com.unicorn.vehicle.ui.CarSelectFra2

class CarSelectPagerAdapter(fm: FragmentManager, private val carRequisition: CarRequisition) :
    FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = mutableListOf("特定车辆类型", "所有车辆")
    }

    init {
        titles[0] = carRequisition.requisitionCarTypeDisplay
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0) CarSelectFra().apply {
            arguments = Bundle().apply {
                putInt(Param, carRequisition.requisitionCarType)
            }
        } else CarSelectFra2()
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}