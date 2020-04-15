package com.unicorn.vehicle.ui

import com.unicorn.vehicle.R
import com.unicorn.vehicle.ui.adapter.pager.ChartsPagerAdapter
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_charts.*

class ChartsFra : BaseFra() {

    override fun initViews() {
        viewPaper.offscreenPageLimit = ChartsPagerAdapter.titles.size - 1
        viewPaper.adapter = ChartsPagerAdapter(childFragmentManager)
        navigationTabStrip.setViewPager(viewPaper)
        navigationTabStrip.setTitles(
            ChartsPagerAdapter.titles[0],
            ChartsPagerAdapter.titles[1],
            ChartsPagerAdapter.titles[2]
        )
    }

    override val layoutId = R.layout.fra_charts

}