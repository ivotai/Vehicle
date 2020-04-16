package com.unicorn.vehicle.ui

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
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
        initChart1()
    }

    private val colorPrimary by lazy { ContextCompat.getColor(context!!, R.color.colorPrimary) }

    private fun initChart1() {
        with(chart3) {

            setScaleEnabled(false)
            description.isEnabled = false

            //

            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
//            xAxis.axisLineColor = colorBlue
            xAxis.textSize = 12f
            xAxis.setDrawAxisLine(false)
//            xAxis.textColor = colorPrimary
            axisLeft.isEnabled = false
            axisRight.isEnabled = false

        }
    }

    override fun bindIntent() {
        getData()
    }

    private fun getData() {
        api.getRequisitionCountForUser()
            .flatMap {
                list1 = it.data
                api.getUsingHoursAverageForUser()
            }
            .observeOnMain(this)
            .subscribe {
                list2 = it.data
                setData()
            }
    }

    private fun setData() {
        val data1 = list1.sortedBy { it.value }.takeLast(10)

        val barEntrys2 = ArrayList<BarEntry>()
        data1.forEachIndexed { index, statisticCommonItem ->
            val value = list2.find { it.name == statisticCommonItem.name }?.value ?: 0.0
            barEntrys2.add(BarEntry(index.toFloat() + 0.5f, value.toFloat()))
        }


        chart3.xAxis.valueFormatter = CarValueFormatter(data1)
        chart3.xAxis.labelCount = data1.size
        chart3.xAxis.mAxisMinimum = 0.0f
        chart3.xAxis.setCenterAxisLabels(true)


        val barEntrys =
            data1.map { BarEntry(data1.indexOf(it).toFloat() + 0.5f, it.value.toFloat()) }

        //


        val barDataSet = BarDataSet(barEntrys, "总使用次数")
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT)
        barDataSet.color = colorPrimary
//        barDataSet.valueTextColor = colorPrimary
//        barDataSet.valueTextSize = 11f

        barDataSet.setDrawValues(true)
        val groupSpace = 0.2f
        val barSpace = 0.00f // x2 dataset

        val barWidth = 0.4f // x2 dataset
        val barDataSet2 = BarDataSet(barEntrys2, "平均使用时间")
        barDataSet2.color = Color.RED
        barDataSet2.setDrawValues(true)
        barDataSet2.valueTextColor = colorPrimary
        barDataSet2.valueTextSize = 11f
        barDataSet2.setAxisDependency(YAxis.AxisDependency.RIGHT)

        barDataSet2.valueFormatter = object : ValueFormatter(){
            override fun getBarLabel(barEntry: BarEntry): String {
                return "${barEntry.y.toInt()}"
            }
        }
        val barData = BarData(barDataSet)
//        barData.setValueTextSize(10f)
        barData.setBarWidth(barWidth)
        barData.setDrawValues(true)
//        barData.groupBars(0f, groupSpace, barSpace)

        chart3.setData(barData)
        chart3.invalidate()
    }


    lateinit var list1: List<StatisticCommonItem>
    lateinit var list2: List<StatisticCommonItem>


    override val layoutId = R.layout.fra_chart3

}