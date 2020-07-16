package com.unicorn.vehicle.data.model

data class LoggedUser(
    val sid: String,
    val uid: String,
    val userName: String,
    val role: Int,
    val roleName: String = "角色",
    val orgID: Int,
    val orgName: String
)