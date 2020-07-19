package com.unicorn.vehicle.app

import com.unicorn.vehicle.data.model.LoggedUser

val uid: String get() = loggedUser.uid

val orgId: Int get() = loggedUser.orgID

lateinit var loggedUser: LoggedUser

var isLogin = false