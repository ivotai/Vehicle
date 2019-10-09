package com.unicorn.vehicle.app

import com.unicorn.vehicle.data.model.LoggedUser

object Global {

    val sid: String get() = loggedUser.sid

    val uid: String get() = loggedUser.uid

    lateinit var loggedUser: LoggedUser

}