package com.unicorn.vehicle.data.model.param

data class CarUsageLogListParam(
    val carID: String? = null,
//    val userID: String? = null,
    val carNo: String? = null,
    val eventType: Int? = null,
    val startTime: String? = null,
    val endTime: String? = null
)