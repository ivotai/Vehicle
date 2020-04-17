package com.unicorn.vehicle.ui

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class IntValueFormatter : ValueFormatter() {

    override fun getBarLabel(barEntry: BarEntry): String {
        return "${barEntry.y.toInt()}"
    }

}