package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.StatisticCommonItem
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_chart1.*

class Chart1Fra : BaseFra() {

    override fun initViews() {
        initChart1()
    }

    private val colorPrimary by lazy { ContextCompat.getColor(context!!, R.color.colorPrimary) }

    private fun initChart1() {
        with(chart1) {
            setScaleEnabled(false)
            description.isEnabled = false
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textSize = 12f
                setDrawAxisLine(false)
            }
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
        }
    }

    override fun bindIntent() {
        getData()
    }

    private fun getData() {
        api.getRequisitionCountForCar()
            .flatMap {
                data1 = it.data
                api.getUsingHoursCountForCar()
            }
            .observeOnMain(this)
            .subscribe {
                data2 = it.data
                setData()
            }
    }

    private fun setData() {
        // 基准 dataSorted1
        val dataSorted1 = data1.sortedBy { it.value }.takeLast(15)   // 1 2 3 ...
        chart1.xAxis.valueFormatter = NameValueFormatter(dataSorted1)
        chart1.xAxis.labelCount = dataSorted1.size

        val barEntrys1 =
            dataSorted1.map { BarEntry(dataSorted1.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet1 = BarDataSet(barEntrys1, "总申请次数（单位“次”）")
        barDataSet1.color = colorPrimary
        barDataSet1.valueTextColor = colorPrimary
        barDataSet1.valueTextSize = 12f
        barDataSet1.axisDependency = YAxis.AxisDependency.LEFT
//        barDataSet.valueFormatter = CarValueFormatter(list)

        // 基准 dataSorted1
        val barEntrys2 = ArrayList<BarEntry>()
        dataSorted1.forEachIndexed { index, item ->
            val value = data2.find { it.name == item.name }?.value ?: 0.0
            barEntrys2.add(BarEntry(index.toFloat(), value.toFloat()))
        }


        val barData = BarData(barDataSet1)
        barData.barWidth = 0.6f

        chart1.data = barData
        chart1.invalidate()
    }

    override val layoutId = R.layout.fra_chart1

    lateinit var data1: List<StatisticCommonItem>
    lateinit var data2: List<StatisticCommonItem>

}