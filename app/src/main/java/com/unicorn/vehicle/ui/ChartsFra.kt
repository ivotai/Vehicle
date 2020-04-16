package com.unicorn.vehicle.ui

import android.graphics.Color
import com.blankj.utilcode.util.ToastUtils
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.ui.adapter.pager.ChartsPagerAdapter
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_charts.*

class ChartsFra : BaseFra() {

    override fun initViews() {
        viewPaper.offscreenPageLimit = ChartsPagerAdapter.titles.size - 1
        viewPaper.adapter = ChartsPagerAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPaper)

        val drawable = IconicsDrawable(context!!)
            .icon(FontAwesome.Icon.faw_calendar_alt)
            .color(Color.WHITE.toIconicsColor())
            .size(24.toIconicsSizeDp())
        fab.setImageDrawable(drawable)
    }

    override fun bindIntent() {
        fab.safeClicks().subscribe {
            ToastUtils.showShort("选择日期range控件找不到，蛋疼")
        }
    }

    private fun showDateRangeDialog() {

    }

    override val layoutId = R.layout.fra_charts

}