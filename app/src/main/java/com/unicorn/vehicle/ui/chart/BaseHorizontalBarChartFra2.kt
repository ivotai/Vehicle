package com.unicorn.vehicle.ui.chart

import android.view.View
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
import com.unicorn.vehicle.data.model.base.Response
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import com.unicorn.vehicle.ui.IntValueFormatter
import com.unicorn.vehicle.ui.NameValueFormatter
import com.unicorn.vehicle.ui.base.BaseFra
import com.unicorn.vehicle.ui.other.Swipe
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fra_base_horizontal_bar_chart.*

abstract class BaseHorizontalBarChartFra2 : BaseFra() {

    abstract val title: String

    abstract val seriesName1: String
    abstract val seriesName2: String

    abstract fun getData1(statisticCommonParam: StatisticCommonParam): Observable<Response<List<StatisticCommonItem>>>
    abstract fun getData2(statisticCommonParam: StatisticCommonParam): Observable<Response<List<StatisticCommonItem>>>

    protected open val useIntValueFormatter1 = true
    protected open val useIntValueFormatter2 = true

    protected open val titleVisible = true

    protected open val autoZoom = true

    override fun initViews() {
        initChart()
        tvTitle.text = title
        if (!titleVisible) tvTitle.visibility = View.GONE
        // 改变颜色试试
        swipe.setColor(mdColor1)
    }

    private fun initChart() {
        with(chart) {
            setScaleEnabled(false)
            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(true)
                axisLineColor = mdColor1
                setCenterAxisLabels(true)
                textColor = mdGrey600
                textSize = 12f
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
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            }
        }
    }

    override fun bindIntent() {
        fetchData()
        swipe.swipeListener = object : Swipe.SwipeListener {
            override fun onSwipe(statisticCommonParam: StatisticCommonParam) {
                fetchData(statisticCommonParam)
            }
        }
    }

    private fun fetchData(statisticCommonParam: StatisticCommonParam = StatisticCommonParam()) {
        getData1(statisticCommonParam)
            .flatMap {
                data1 = it.data
                getData2(statisticCommonParam)
            }
            .observeOnMain(this)
            .subscribe {
                data2 = it.data
                renderChart()
            }
    }

    private fun renderChart() = with(chart) {
        // 基准 dataSorted1
        val dataSorted1 = data1.sortedBy { it.value }  // 1 2 3 ...

        // groupCount
        val groupCount = dataSorted1.size

        //
        chart.xAxis.valueFormatter = NameValueFormatter(dataSorted1)
        chart.xAxis.labelCount = dataSorted1.size

        //
        val barEntrys1 = ArrayList<BarEntry>()
        dataSorted1.forEachIndexed { index, item ->
            barEntrys1.add(
                BarEntry(
                    index.toFloat(),
                    item.value.toFloat()
                )
            )
        }
        val barDataSet1 = BarDataSet(barEntrys1, seriesName1).apply {
            color = mdColor1
            valueTextColor = mdColor1
            valueTextSize = 12f
            axisDependency = YAxis.AxisDependency.LEFT
            if (useIntValueFormatter1) valueFormatter = IntValueFormatter()
        }

        // 基准 dataSorted1
        val barEntrys2 = ArrayList<BarEntry>()
        dataSorted1.forEachIndexed { index, item ->
            val value = data2.find { it.name == item.name }?.value ?: 0.0
            barEntrys2.add(BarEntry(index.toFloat() + 0, value.toFloat()))
        }
        val barDataSet2 = BarDataSet(barEntrys2, seriesName2).apply {
            color = mdColor2
            valueTextColor = mdColor2
            valueTextSize = 12f
            axisDependency = YAxis.AxisDependency.RIGHT
            if (useIntValueFormatter2) valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet1, barDataSet2)
        barData.barWidth = barWidth
        data = barData

        val groupSpace = 0.2f
        val barSpace = 0.00f // x2 DataSet
//        val barWidth = 0.4f // x2 DataSet
        xAxis.axisMinimum = data.xMin
//        xAxis.axisMaximum = 0 + barData.getGroupWidth(groupSpace, barSpace) * groupCount
        xAxis.axisMaximum = data.xMax
        groupBars(0.toFloat(), groupSpace, barSpace)

        invalidate()
        animateY(800)

        if (autoZoom) zoom(dataSorted1.size)
    }

    private fun zoom(size: Int) = with(chart) {
        // some problem with resetZoom
//        resetZoom()
        fitScreen()
        zoom(1f, size.toFloat() / displayCount, 0f, 0f)
        // 很奇怪把 xMax 的值放到 y 上...
        moveViewTo(0f, data.entryCount.toFloat(), YAxis.AxisDependency.LEFT)
    }

    private val displayCount = 10f
    private val barWidth = 0.4f

    private val mdColor1 by lazy { ContextCompat.getColor(context!!, R.color.colorPrimary) }
    private val mdColor2 by lazy { ContextCompat.getColor(context!!, R.color.md_teal_300) }
    private val mdGrey600 by lazy { ContextCompat.getColor(context!!, R.color.md_grey_600) }

    lateinit var data1: List<StatisticCommonItem>
    lateinit var data2: List<StatisticCommonItem>

    override val layoutId: Int = R.layout.fra_base_horizontal_bar_chart

}