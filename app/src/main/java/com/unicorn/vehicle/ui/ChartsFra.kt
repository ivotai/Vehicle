package com.unicorn.vehicle.ui

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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
        initChart1()
    }

    private val colorPrimary by lazy { ContextCompat.getColor(context!!,R.color.colorPrimary) }
    private fun initChart1() {
        with(chart1) {

            setScaleEnabled(false)
            description.isEnabled =false

            //

            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.axisLineColor = colorPrimary
            xAxis.textSize = 12f
            xAxis.setDrawAxisLine(false)
//            xAxis.textColor = colorPrimary
            axisLeft.isEnabled = false
            axisRight.isEnabled =false

            legend.isEnabled =false
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
        val data = list.sortedBy { it.value }

        chart1.xAxis.valueFormatter = CarValueFormatter(data)
        chart1.xAxis.labelCount = data.size


        val barEntrys = data.map { BarEntry(data.indexOf(it).toFloat(), it.value.toFloat()) }
        val barDataSet = BarDataSet(barEntrys,"")
        barDataSet.color = colorPrimary
        barDataSet.valueTextColor  = colorPrimary
        barDataSet.valueTextSize = 11f
        barDataSet.valueFormatter = CarValueFormatter(list)


//        val dataSet = ArrayList<IBarDataSet>()
//        dataSet.add(barDataSet)

        val barData = BarData(barDataSet)
//        barData.setValueTextSize(10f)
        barData.setBarWidth(0.7f)

        chart1.setData(barData)


        chart1.invalidate()
    }

    private var dateStart = DateTime().minusMonths(1).toString(displayDateFormat)

    private var dateEnd = DateTime().toString(displayDateFormat)

    override val layoutId = R.layout.fra_charts

}