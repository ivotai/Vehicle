package com.unicorn.vehicle.data.model

import com.unicorn.vehicle.app.helper.EncryptionHelper

data class LoggedUser(
    val encryptionId: String,
    val orgID: Int,
    val orgName: String,
    val role: Int,
    val roleName: String = "角色",
//    val sid: String,
    val userKey: String,
    val userName: String,
    val userToken: String
) {
    val uid: String get() = EncryptionHelper.decrypt(encryptionId)
}