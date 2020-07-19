package com.unicorn.vehicle.data.model

import java.io.Serializable
import java.util.*

data class CarRequisition(
    var approvalRemark: String,
    val approvalServerTime: Date,
    val approvalUserID: String,
    val approvalUserName: String,
    val keyBackServerTime: Date,
    val keyBoxPassword: Any,
    val keyPickUpServerTime: Date,
    var requisitionCarID: Int? = null,
    val requisitionCarName: String,
    val requisitionCarType: Int,
    val requisitionCarTypeDisplay: String,
    val requisitionCause: Int,
    val requisitionCarNo: String,
    val requisitionCauseDisplay: String,
    val requisitionFromType: Int,
    val requisitionFromTypeDisplay: String,
    val requisitionID: String,
    val requisitionServerTime: Date,
    val requisitionUserID: String,
    val requisitionUserName: String,
    val requisitionStartTime: Date,
    val requisitionEndTime: Date,
    val requisitionDestination: String,
    val state: Int,
    val stateDisplay: String
) : Serializable, BasePostInfo()