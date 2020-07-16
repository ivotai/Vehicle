package com.unicorn.vehicle.data.model.param

import com.unicorn.vehicle.app.orgId

data class CarRequisitionListParam(
    val requisitionCarType: Int? = null,
    val approvalUserID: String? = null,
    val states: List<Int>,
    val orgID: Int = orgId
)