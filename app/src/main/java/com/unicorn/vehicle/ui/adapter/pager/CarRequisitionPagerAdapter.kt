package com.unicorn.vehicle.ui.adapter.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.ui.CarRequisitionListFra

class CarRequisitionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val titles = listOf("待处理", "已通过", "未通过")
    }

    override fun getItem(position: Int): Fragment {
        val state = when (position) {
            0 -> 0
            1 -> 1
            else -> 2
        }
        val bundle = Bundle()
        bundle.putInt(Key.CarRequisitionState, state)
        val fra = CarRequisitionListFra()
        fra.arguments = bundle
        return fra
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]

}