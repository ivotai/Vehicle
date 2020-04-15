package com.unicorn.vehicle.ui

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.unicorn.vehicle.data.model.StatisticCommonItem

class CarValueFormatter(private val data: List<StatisticCommonItem>) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        val index = value.toInt()
        return data[index].name
    }

    override fun getBarLabel(barEntry: BarEntry): String {
        return "${barEntry.y.toInt()}次"
    }
}