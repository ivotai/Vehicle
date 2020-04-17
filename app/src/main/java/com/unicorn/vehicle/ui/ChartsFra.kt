package com.unicorn.vehicle.ui

import android.graphics.Color
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.displayDateFormat
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import com.unicorn.vehicle.ui.adapter.pager.ChartsPagerAdapter
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_charts.*
import org.joda.time.DateTime

class ChartsFra : BaseFra() {

    override fun initViews() {
        viewPaper.offscreenPageLimit = ChartsPagerAdapter.titles.size - 1
        viewPaper.adapter = ChartsPagerAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPaper)

        val drawable = IconicsDrawable(context!!)
            .icon(FontAwesome.Icon.faw_calendar_alt)
            .color(Color.WHITE.toIconicsColor())
            .size(24.toIconicsSizeDp())
//        fab.setImageDrawable(drawable)
    }

    override fun bindIntent() {
//        fab.safeClicks().subscribe { showDateRange() }
    }

    private fun showDateRange() {
        MaterialDialog(context!!).show {
            title(text = "选择开始日期")
            datePicker { _, dateStart ->
                statisticCommonParam.dateStart = DateTime(dateStart).toString(displayDateFormat)
                showEndDateDialog()
            }
        }
    }

    private fun showEndDateDialog() {
        MaterialDialog(context!!).show {
            title(text = "选择结束日期")
            datePicker { _, dateEnd ->
                statisticCommonParam.dateEnd = DateTime(dateEnd).toString(displayDateFormat)
                RxBus.post(statisticCommonParam)
            }
        }
    }

    private val statisticCommonParam = StatisticCommonParam()

    override val layoutId = R.layout.fra_charts

}