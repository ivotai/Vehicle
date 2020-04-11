package com.unicorn.vehicle.data.model

import android.graphics.Color
import com.unicorn.vehicle.R

data class CarUsageLog(
    val carID: Int,
    val carNo: String,
    val eventType: Int,
    val eventTypeDisplay: String,
    val id: Int,
    val serverTime: String,
    val userID: String,
    val userName: String
) {
    val eventTypeTextColor: Int
        get() = when (eventType) {
            0, 3 -> Color.parseColor("#1BC36B")
            else -> Color.parseColor("#FF9D01")
        }
    val eventTypeResId: Int
        get() = when (eventType) {
            0 -> R.mipmap.rk
            1 -> R.mipmap.ck
            2 -> R.mipmap.hqys
            else -> R.mipmap.ghys
        }
}