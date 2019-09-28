package com.unicorn.vehicle.data.model

data class Apply(
    val person: String,
    val reason: String,
    val startTime: String,
    val endTime: String,
    val destination: String,
    val vehicleType: String
) {

    lateinit var vehicle: Vehicle
}

/*
    基本信息
    申请人
    申请事由

    其他信息
    用车时间
    还车时间
    目的地

    车辆信息
    车辆类型
    所选车辆
 */

