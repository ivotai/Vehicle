package com.unicorn.vehicle.ui

import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.displayDateFormat
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.StatisticCommonItem
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_charts.*
import org.joda.time.DateTime

class ChartsFra : BaseFra() {

    override fun initViews() {

    }

    private fun initChart1() {
        with(chart1) {
            setDrawBarShadow(true)
            setDrawValueAboveBar(true)
            getDescription().setEnabled(true)

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
//            chart.setMaxVisibleValueCount(60)

            // scaling can now only be done on x- and y-axis separately

            // scaling can now only be done on x- and y-axis separately
            setPinchZoom(true)

            setDrawGridBackground(true)
            // chart.setDrawYLabels(false);

            //

            val xAxis: XAxis = getXAxis()
            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f // only intervals of 1 day

            xAxis.labelCount = 7
//            xAxis.valueFormatter = xAxisFormatter

            val custom: ValueFormatter =
                MyValueFormatter("$")

            val leftAxis: YAxis =
                getAxisLeft()
            leftAxis.setLabelCount(8, false)
            leftAxis.valueFormatter = custom
            leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.spaceTop = 15f
            leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

            val l: Legend = getLegend()
            l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            l.orientation = Legend.LegendOrientation.HORIZONTAL
            l.setDrawInside(false)
            l.form = LegendForm.SQUARE
            l.formSize = 9f
            l.textSize = 11f
            l.xEntrySpace = 4f

        }
    }

    override fun bindIntent() {
        getRequisitionCountForCar()
    }

    private fun getRequisitionCountForCar() {
        api.getRequisitionCountForCar(
            StatisticCommonParam(
                dateStart = dateStart,
                dateEnd = dateEnd
            )
        )
            .observeOnMain(this)
            .subscribe { setData(it.data) }
    }

    private fun setData(list: List<StatisticCommonItem>) {
        val barEntrys = list.map { BarEntry(list.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys,"速度")
        barDataSet.setDrawIcons(true)
        val dataSet = ArrayList<IBarDataSet>()
        dataSet.add(barDataSet)

        val barData = BarData(dataSet)
        barData.setValueTextSize(10f)
        barData.setBarWidth(0.9f)

        chart1.setData(barData)
    }

    private var dateStart = DateTime().minusMonths(1).toString(displayDateFormat)

    private var dateEnd = DateTime().toString(displayDateFormat)

    override val layoutId = R.layout.fra_charts

}