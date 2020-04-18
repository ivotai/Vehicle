package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
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
import kotlinx.android.synthetic.main.fra_chart1.view.*

class Chart1Fra : BaseFra() {

    override fun initViews() {
        initChart()
    }

    private fun initChart() {
        with(chart1) {
//            setScaleEnabled(false)

            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textSize = 12f
                setDrawAxisLine(false)
                textColor = md_grey_600
                // 神技能
                xAxis.granularity = 1f
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
        // 改变颜色试试
        swipe1.setColor(mdColor1)
        swipe2.setColor(mdColor2)
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
        api.getUsageCountForCar(statisticCommonParam)
            .observeOnMain(this)
            .subscribe { setData1(it.data) }
    }

    private fun getData2(statisticCommonParam: StatisticCommonParam = StatisticCommonParam()) {
        api.getUsingHoursCountForCar(statisticCommonParam)
            .observeOnMain(this)
            .subscribe { setData2(it.data) }
    }

    private val textSize= 0f
    private val defaultGroupCount = 10

    private fun setData1(data: List<StatisticCommonItem>) {
        val dataSorted = data.sortedBy { it.value }  // 1 2 3 ...
        val size = dataSorted.size


//        if (dataSorted.size > defaultGroupCount) dataSorted = dataSorted.takeLast(defaultGroupCount)

        chart1.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart1.xAxis.labelCount = dataSorted.size

        val barEntrys =
            dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "总申请次数").apply {
            color = mdColor1
            valueTextColor = mdColor1
            valueTextSize = 300f/size.toFloat()/2f
            valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet)
        with(chart1) {
            chart1.data = barData
            barData.barWidth = 0.7f
            invalidate()
            animateY(1000)

            chart1.zoomToCenter(1f,size.toFloat()/10f)
            chart1.moveViewTo(0f,barData.yMax,YAxis.AxisDependency.LEFT)
        }
    }

    private fun changeChartHeight(chart: HorizontalBarChart, size: Int) {
        val params = chart.layoutParams
        chart.layoutParams.height = ConvertUtils.dp2px(maxOf(size, 10) * 30f)
        chart.layoutParams.height = 0
        chart.layoutParams = params
    }

    private fun setData2(data: List<StatisticCommonItem>) {
        changeChartHeight(chart2, data.size)
        var dataSorted = data.sortedBy { it.value }  // 1 2 3 ...
//        if (dataSorted.size > defaultGroupCount) dataSorted = dataSorted.takeLast(defaultGroupCount)

        chart2.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart2.xAxis.labelCount = dataSorted.size

        val barEntrys =
            dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "总使用时间（小时）").apply {
            color = mdColor2
            valueTextColor = mdColor2
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

    private val mdColor1 by lazy { ContextCompat.getColor(context!!, R.color.md_indigo_300) }
    private val md_grey_600 by lazy { ContextCompat.getColor(context!!, R.color.md_grey_600) }
    private val mdColor2 by lazy { ContextCompat.getColor(context!!, R.color.md_green_300) }

    override val layoutId = R.layout.fra_chart1

}