package com.unicorn.vehicle.ui.chart

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
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_base_horizontal_bar_chart.*

abstract class BaseHorizontalBarChartFra : BaseFra() {

    abstract val title: String

    abstract val seriesName: String

    abstract fun getData(statisticCommonParam: StatisticCommonParam): Observable<Response<List<StatisticCommonItem>>>

    protected open val useIntValueFormatter = true

    override fun initViews() {
        initChart()
        tvTitle.text = title
        // 改变颜色试试
        swipe.setColor(mdColor)
    }

    private fun initChart() {
        with(chart) {
            setScaleEnabled(false)
            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(true)
                axisLineColor = mdColor
                textColor = mdGrey600
                textSize = 12f
                // 神技能
                xAxis.granularity = 1f
            }
            // 影藏坐标轴
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            // 确保了对齐
//            axisRight.axisMinimum = 0f
            axisLeft.axisMinimum = 0f
            with(legend) {
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
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
        getData(statisticCommonParam = statisticCommonParam)
            .observeOnMain(this)
            .subscribeBy(
                onNext = {
                    renderChart(it.data)
                },
                onError = {
                }
            )
    }

    private fun renderChart(list: List<StatisticCommonItem>) = with(chart) {
        val dataSorted = list.sortedBy { it.value } // 1 2 3...

        //
        chart.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart.xAxis.labelCount = dataSorted.size

        //
        val barEntrys = ArrayList<BarEntry>()
        dataSorted.forEachIndexed { index, item ->
            barEntrys.add(
                BarEntry(
                    index.toFloat(),
                    item.value.toFloat()
                )
            )
        }
        val barDataSet = BarDataSet(barEntrys, seriesName).apply {
            color = mdColor
            valueTextColor = mdColor
            valueTextSize = 12f
            if (useIntValueFormatter) valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet)
        barData.barWidth = barWidth
        data = barData
        invalidate()
        animateY(1000)

        zoom(dataSorted.size)
    }

    private fun zoom(size: Int) = with(chart) {
        // some problem with resetZoom
//        resetZoom()
        fitScreen()
        zoom(1f, size.toFloat() / displayCount, 0f, 0f)
        scaleY
        scaleX
        // 很奇怪把 xMax 的值放到 y 上...
        moveViewTo(0f, barData.xMax, YAxis.AxisDependency.LEFT)
    }

    private val displayCount = 15f
    private val barWidth = 0.7f

    private val mdColor by lazy { ContextCompat.getColor(context!!, R.color.md_indigo_300) }
    private val mdGrey600 by lazy { ContextCompat.getColor(context!!, R.color.md_grey_600) }

    override val layoutId: Int = R.layout.fra_base_horizontal_bar_chart

}