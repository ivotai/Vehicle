package com.unicorn.vehicle.data.model.param

import com.unicorn.vehicle.app.loggedUser

data class CarListParam(
    val carType: Int? = null,
    val carState: Int? = 0,
    val name: String? = null,
    val no: String? = null,
    val orgID: Int = loggedUser.orgID
)