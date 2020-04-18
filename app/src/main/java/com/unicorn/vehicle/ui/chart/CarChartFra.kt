package com.unicorn.vehicle.ui.chart

import com.unicorn.vehicle.R
import com.unicorn.vehicle.ui.adapter.pager.CarChartPagerAdapter
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_chart_car.*

class CarChartFra : BaseFra() {

    override fun initViews() {
        viewPaper.offscreenPageLimit = CarChartPagerAdapter.titles.size - 1
        viewPaper.adapter = CarChartPagerAdapter(childFragmentManager)
    }

    override val layoutId = R.layout.fra_chart_car

}