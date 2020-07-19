package com.unicorn.vehicle.data.model

import com.unicorn.vehicle.app.loggedUser

open class BasePostInfo(var idCardNumber: String? = loggedUser.uid)