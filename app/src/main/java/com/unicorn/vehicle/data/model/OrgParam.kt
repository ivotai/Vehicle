package com.unicorn.vehicle.data.model

import com.unicorn.vehicle.app.loggedUser

data class OrgParam(
    val orgID: Int = loggedUser.orgID
) : BasePostInfo()