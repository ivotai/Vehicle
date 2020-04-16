package com.unicorn.vehicle.ui

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.unicorn.vehicle.data.model.StatisticCommonItem

class NameValueFormatter(private val data: List<StatisticCommonItem>) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        val index = value.toInt()
        return if (index in data.indices) data[index].name else ""
    }

}