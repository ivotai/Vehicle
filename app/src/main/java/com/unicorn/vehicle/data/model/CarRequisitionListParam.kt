package com.unicorn.vehicle.data.model

data class CarRequisitionListParam(
    val requisitionCarType: Int? = null,
    val state: Int? = null, // 0
    val requisitionUserID: String? = null
)