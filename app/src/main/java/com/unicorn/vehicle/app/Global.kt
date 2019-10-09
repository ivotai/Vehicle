package com.unicorn.vehicle.app

import com.unicorn.vehicle.data.model.LoggedUser

object Global {

    val sid = loggedUser.sid

    lateinit var loggedUser: LoggedUser

}