package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
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
                textSize = 14f
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
        val dataSorted = data1.sortedBy { it.value }   // 1 2 3 ...

//        chart1.xAxis.valueFormatter = CarValueFormatter(data)
        chart1.xAxis.labelCount = dataSorted.size


        val barEntrys = dataSorted.map { BarEntry(dataSorted.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys, "")
        barDataSet.color = colorPrimary
        barDataSet.valueTextColor = colorPrimary
        barDataSet.valueTextSize = 11f
//        barDataSet.valueFormatter = CarValueFormatter(list)


//        val dataSet = ArrayList<IBarDataSet>()
//        dataSet.add(barDataSet)

        val barData = BarData(barDataSet)
//        barData.setValueTextSize(10f)
        barData.setBarWidth(0.6f)

        chart1.setData(barData)


        chart1.invalidate()
    }

    override val layoutId = R.layout.fra_chart1

    lateinit var data1: List<StatisticCommonItem>
    lateinit var data2: List<StatisticCommonItem>

}