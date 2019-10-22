package com.unicorn.vehicle.app

import com.unicorn.vehicle.data.model.LoggedUser
import com.unicorn.vehicle.data.model.UserLoginParam

object Globals {

    val sid: String get() = loggedUser.sid

    val uid: String get() = loggedUser.uid

    lateinit var loggedUser: LoggedUser

    lateinit var userLoginParam: UserLoginParam

}