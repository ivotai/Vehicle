package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.StatisticCommonItem
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import com.unicorn.vehicle.ui.base.BaseFra
import com.unicorn.vehicle.ui.other.Swipe
import kotlinx.android.synthetic.main.fra_chart1.*

class Chart1Fra : BaseFra() {

    override fun initViews() {
        initChart()
    }

    private fun initChart() {
        with(chart1) {
            setScaleEnabled(false)
            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textSize = 12f
                setDrawAxisLine(false)
                textColor = md_grey_600
            }
            // 影藏坐标轴
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            // 确保了对齐
            axisRight.axisMinimum = 0f
            axisLeft.axisMinimum = 0f

            with(legend) {
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            }
        }
        with(chart2) {
            setScaleEnabled(false)
            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textSize = 12f
                setDrawAxisLine(false)
                textColor = md_grey_600
            }
            // 影藏坐标轴
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            // 确保了对齐
            axisRight.axisMinimum = 0f
            axisLeft.axisMinimum = 0f

            with(legend) {
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            }
        }
    }

    override fun bindIntent() {
        getData1()
        swipe1.swipeListener = object : Swipe.SwipeListener {
            override fun onSwipe(statisticCommonParam: StatisticCommonParam) {
                getData1(statisticCommonParam)
            }
        }
        getData2()
        swipe2.swipeListener = object : Swipe.SwipeListener {
            override fun onSwipe(statisticCommonParam: StatisticCommonParam) {
                getData2(statisticCommonParam)
            }
        }
    }

    private fun getData1(statisticCommonParam: StatisticCommonParam = StatisticCommonParam()) {
        api.getRequisitionCountForCar(statisticCommonParam)
            .observeOnMain(this)
            .subscribe { setData1(it.data) }
    }

    private fun getData2(statisticCommonParam: StatisticCommonParam = StatisticCommonParam()) {
        api.getUsingHoursCountForCar(statisticCommonParam)
            .observeOnMain(this)
            .subscribe { setData2(it.data) }
    }

    private val defaultGroupCount = 10

    private fun setData1(data: List<StatisticCommonItem>) {
        var dataSorted = data.sortedBy { it.value }  // 1 2 3 ...
        if (dataSorted.size > defaultGroupCount) dataSorted = dataSorted.takeLast(defaultGroupCount)

        chart1.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart1.xAxis.labelCount = dataSorted.size

        val barEntrys =
            dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "总申请次数").apply {
            color = colorPrimary
            valueTextColor = colorPrimary
            valueTextSize = 12f
            valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet)
        with(chart1) {
            chart1.data = barData
            barData.barWidth = 0.7f
            invalidate()
            animateY(1000)
        }
    }

    private fun setData2(data: List<StatisticCommonItem>) {
        var dataSorted = data.sortedBy { it.value }  // 1 2 3 ...
        if (dataSorted.size > defaultGroupCount) dataSorted = dataSorted.takeLast(defaultGroupCount)

        chart2.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart2.xAxis.labelCount = dataSorted.size

        val barEntrys =
            dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "总使用时间（小时）").apply {
            color = colorPrimary
            valueTextColor = colorPrimary
            valueTextSize = 12f
            valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet)
        with(chart2) {
            chart2.data = barData
            barData.barWidth = 0.7f
            invalidate()
            animateY(1000)
        }
    }

    private val colorPrimary by lazy { ContextCompat.getColor(context!!, R.color.colorPrimary) }
    private val md_grey_600 by lazy { ContextCompat.getColor(context!!, R.color.md_grey_600) }

    override val layoutId = R.layout.fra_chart1

}