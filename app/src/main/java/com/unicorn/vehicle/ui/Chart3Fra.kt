package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.StatisticCommonItem
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_chart3.*

class Chart3Fra : BaseFra() {

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
            }
            // 影藏坐标轴
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            // 确保了对齐
            axisRight.axisMinimum = 0f
            axisLeft.axisMinimum = 0f

            with(legend){
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            }
        }
    }

    override fun bindIntent() {
        getData()
    }


    private fun getData() {
        api.getRequisitionCountForCar()
            .observeOnMain(this)
            .subscribe {
                data1 = it.data
                setData()
            }
    }

    private val defaultGroupCount = 10

    private fun setData() {
        // 基准 dataSorted1
        var dataSorted = data1.sortedBy { it.value }  // 1 2 3 ...
        if (dataSorted.size > defaultGroupCount) dataSorted = dataSorted.takeLast(defaultGroupCount)

        chart1.xAxis.valueFormatter = NameValueFormatter(dataSorted)
        chart1.xAxis.labelCount = dataSorted.size

        val barEntrys =
            dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "总申请次数（单位“次”，按车辆分组）").apply {
            color = colorPrimary
            valueTextColor = colorPrimary
            valueTextSize = 12f
            valueFormatter = object : ValueFormatter() {
                override fun getBarLabel(barEntry: BarEntry): String {
                    return "${barEntry.y.toInt()}"
                }
            }
        }


        val barData = BarData(barDataSet)

        with(chart1) {
            data = barData

            barData.barWidth = 0.7f


            invalidate()
            animateY(1000)
        }

    }

    private val colorPrimary by lazy { ContextCompat.getColor(context!!, R.color.colorPrimary) }
    private val colorMd by lazy { ContextCompat.getColor(context!!, R.color.md_teal_400) }

    lateinit var data1: List<StatisticCommonItem>
    lateinit var data2: List<StatisticCommonItem>

    override val layoutId = R.layout.fra_chart3

}