package com.unicorn.vehicle.data.model

data class CheckAppVersionResponse(
    val needUpdate: Boolean,
    val url: String
)