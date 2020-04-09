package com.unicorn.vehicle.app

import com.chibatching.kotpref.KotprefModel

object AppInfo : KotprefModel() {
    var LoginStr by stringPref()
    var UserPwd by stringPref()
}