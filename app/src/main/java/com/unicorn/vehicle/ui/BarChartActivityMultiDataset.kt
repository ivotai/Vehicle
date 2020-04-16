package com.unicorn.vehicle.ui

import android.graphics.Color
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_chart1.*
import java.util.*

class BarChartActivityMultiDataset : BaseFra() {

    private var groupCount = 0

    override val layoutId: Int = com.unicorn.vehicle.R.layout.fra_chart1
    override fun initViews() {
        with(chart1) {

            xAxis.granularity = 1f
            xAxis.setCenterAxisLabels(true)

            val leftAxis = axisLeft
            leftAxis.setDrawGridLines(false)
            leftAxis.spaceTop = 35f
            leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
            axisRight.isEnabled = false
        }

        onProgressChanged(15)
    }


    fun onProgressChanged(

        groupCount: Int

    ) {

        val groupSpace = 0.1f
        val barSpace = 0.00f // x4 DataSet
        val barWidth = 0.45f // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        val startYear = 0
        val endYear = startYear + groupCount
        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()

        val randomMultiplier =   100000f
        for (i in startYear until endYear) {
            values1.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))
            values2.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))

        }
        val set1: BarDataSet
        val set2: BarDataSet

        if (chart1!!.data != null && chart1!!.data.dataSetCount > 0) {
            set1 = chart1!!.data.getDataSetByIndex(0) as BarDataSet
            set2 = chart1!!.data.getDataSetByIndex(1) as BarDataSet

            set1.values = values1
            set2.values = values2

            chart1!!.data.notifyDataChanged()
            chart1!!.notifyDataSetChanged()
        } else {
            // create 4 DataSets
            set1 = BarDataSet(values1, "Company A")
            set1.color = Color.rgb(104, 241, 175)
            set2 = BarDataSet(values2, "Company B")
            set2.color = Color.rgb(164, 228, 251)

            val data = BarData(set1, set2)
            data.setValueFormatter(LargeValueFormatter())

            chart1!!.data = data
        }

        // specify the width each bar should have
        chart1!!.barData.barWidth = barWidth

        // restrict the x-axis range
        chart1!!.xAxis.axisMinimum = startYear.toFloat()

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart1!!.xAxis.axisMaximum =
            startYear + chart1!!.barData.getGroupWidth(groupSpace, barSpace) * groupCount
        chart1!!.groupBars(startYear.toFloat(), groupSpace, barSpace)
        chart1!!.invalidate()
    }

}

