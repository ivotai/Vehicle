package com.unicorn.vehicle.data.model

data class CarUsageLog(
    val carID: Int,
    val carNo: String,
    val eventType: Int,
    val eventTypeDisplay: String,
    val id: Int,
    val serverTime: String,
    val userID: String,
    val userName: String
)