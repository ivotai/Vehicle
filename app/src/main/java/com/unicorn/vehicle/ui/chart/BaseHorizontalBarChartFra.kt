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
import kotlinx.android.synthetic.main.fra_base_horizontal_bar_chart.*

abstract class BaseHorizontalBarChartFra : BaseFra() {

    abstract fun api(statisticCommonParam: StatisticCommonParam): Observable<Response<List<StatisticCommonItem>>>

    override fun initViews() {
        initChart()
    }

    private fun initChart() {
        with(chart) {
//            setScaleEnabled(false)

            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textSize = 12f
                setDrawAxisLine(false)
                textColor = mdGrey600
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

        // 改变颜色试试
        swipe.setColor(mdColor)
    }

    override fun bindIntent() {
        getData()
        swipe.swipeListener = object : Swipe.SwipeListener {
            override fun onSwipe(statisticCommonParam: StatisticCommonParam) {
                getData(statisticCommonParam)
            }
        }

    }

    private fun getData(statisticCommonParam: StatisticCommonParam = StatisticCommonParam()) {
        api(statisticCommonParam)
            .observeOnMain(this)
            .subscribe { setData1(it.data) }
    }

    private fun setData1(data: List<StatisticCommonItem>) {
        val dataSorted = data.sortedBy { it.value }  // 1 2 3 ...
        val size = dataSorted.size


//        if (dataSorted.size > defaultGroupCount) dataSorted = dataSorted.takeLast(defaultGroupCount)

        chart.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart.xAxis.labelCount = dataSorted.size

        val barEntrys =
            dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "总申请次数").apply {
            color = mdColor
            valueTextColor = mdColor
            valueTextSize = 300f/size.toFloat()/2f
            valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet)
        with(chart) {
            chart.data = barData
            barData.barWidth = 0.7f
            invalidate()
            animateY(1000)

            chart.zoomToCenter(1f,size.toFloat()/10f)
            chart.moveViewTo(0f,barData.yMax, YAxis.AxisDependency.LEFT)
        }
    }

    private val mdColor by lazy { ContextCompat.getColor(context!!, R.color.md_indigo_300) }
    private val mdGrey600 by lazy { ContextCompat.getColor(context!!, R.color.md_grey_600) }

    override val layoutId: Int = R.layout.fra_base_horizontal_bar_chart

}