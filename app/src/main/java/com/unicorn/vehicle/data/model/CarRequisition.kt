package com.unicorn.vehicle.data.model

import java.io.Serializable

data class CarRequisition(
    val approvalRemark: String,
    val approvalServerTime: String,
    val approvalUserID: String,
    val approvalUserName: String,
    val keyBackServerTime: Any,
    val keyBoxPassword: Any,
    val keyPickUpServerTime: Any,
    var requisitionCarID: Int? = null,
    val requisitionCarName: String,
    val requisitionCarType: Int,
    val requisitionCarTypeDisplay: String,
    val requisitionCause: String,
    val requisitionFromType: Int,
    val requisitionFromTypeDisplay: String,
    val requisitionID: String,
    val requisitionServerTime: String,
    val requisitionUserID: String,
    val requisitionUserName: String,
    val state: Int,
    val stateDisplay: String
) : Serializable