package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.fra_chart2.*

class Chart2Fra : BaseFra() {

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
                setCenterAxisLabels(true)
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
//                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            }
        }
    }

    override fun bindIntent() {
        getData()
        swipe1.swipeListener = object : Swipe.SwipeListener {
            override fun onSwipe(statisticCommonParam: StatisticCommonParam) {
                getData(statisticCommonParam)
            }
        }
    }

    private fun getData(statisticCommonParam: StatisticCommonParam = StatisticCommonParam()) {
        api.getRequisitionCountForUser(statisticCommonParam)
            .flatMap {
                data1 = it.data
                api.getUsingHoursAverageForUser(statisticCommonParam)
            }
            .observeOnMain(this)
            .subscribe {
                data2 = it.data
                setData()
            }
    }

    private val defaultGroupCount = 10

    private fun setData() {
        // 基准 dataSorted1
        var dataSorted1 = data1.sortedBy { it.value }  // 1 2 3 ...

        //
        var groupCount = defaultGroupCount
        if (dataSorted1.size > defaultGroupCount) dataSorted1 =
            dataSorted1.takeLast(defaultGroupCount)
        else groupCount = dataSorted1.size

        chart1.xAxis.valueFormatter = NameValueFormatter(dataSorted1)
        chart1.xAxis.labelCount = dataSorted1.size

        val barEntrys1 =
            dataSorted1.map { BarEntry(dataSorted1.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet1 = BarDataSet(barEntrys1, "总申请次数").apply {
            color = colorPrimary
            valueTextColor = colorPrimary
            valueTextSize = 12f
            axisDependency = YAxis.AxisDependency.LEFT
            valueFormatter = IntValueFormatter()
        }

        // 基准 dataSorted1
        val barEntrys2 = ArrayList<BarEntry>()
        dataSorted1.forEachIndexed { index, item ->
            val value = data2.find { it.name == item.name }?.value ?: 0.0
            barEntrys2.add(BarEntry(index.toFloat() + 0, value.toFloat()))
        }
        val barDataSet2 = BarDataSet(barEntrys2, "平均每次使用时间（小时）").apply {
            color = colorMd
            valueTextColor = colorMd
            valueTextSize = 12f
            axisDependency = YAxis.AxisDependency.RIGHT
            valueFormatter = IntValueFormatter()
        }

        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
        val barData = BarData(barDataSet1, barDataSet2)

        with(chart1) {
            data = barData

            val groupSpace = 0.2f
            val barSpace = 0.00f // x2 DataSet
            val barWidth = 0.4f // x2 DataSet

            // specify the width each bar should have
            barData.barWidth = barWidth

            // restrict the x-axis range
            xAxis.axisMinimum = 0.toFloat()

            // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
            xAxis.axisMaximum = 0 + barData.getGroupWidth(groupSpace, barSpace) * groupCount
            groupBars(0.toFloat(), groupSpace, barSpace)
            invalidate()
            animateY(1000)
        }

    }

    private val colorPrimary by lazy { ContextCompat.getColor(context!!, R.color.colorPrimary) }
    private val colorMd by lazy { ContextCompat.getColor(context!!, R.color.md_teal_300) }
    private val md_grey_600 by lazy { ContextCompat.getColor(context!!, R.color.md_grey_600) }

    lateinit var data1: List<StatisticCommonItem>
    lateinit var data2: List<StatisticCommonItem>

    override val layoutId = R.layout.fra_chart2

}