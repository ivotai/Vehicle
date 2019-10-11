package com.unicorn.vehicle.data.model

data class CarRequisitionListParam(
    val requisitionCarType: Int? = null,
    val approvalUserID: String? = null,
    val states: List<Int>
)