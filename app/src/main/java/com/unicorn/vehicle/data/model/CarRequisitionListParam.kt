package com.unicorn.vehicle.data.model

import com.unicorn.vehicle.app.Global

data class CarRequisitionListParam(
    val requisitionCarType: Int? = null,
    val state: Int? = null,
    val requisitionUserID: String = Global.uid
)