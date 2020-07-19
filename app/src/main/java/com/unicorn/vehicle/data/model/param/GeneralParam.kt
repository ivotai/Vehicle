package com.unicorn.vehicle.data.model.param

import com.unicorn.vehicle.app.di.Holder
import com.unicorn.vehicle.app.helper.EncryptionHelper
import com.unicorn.vehicle.app.loggedUser
import com.unicorn.vehicle.data.model.BasePostInfo
import com.unicorn.vehicle.data.model.OrgParam

data class GeneralParam(
    val Param: String,
    val UserToken: String = loggedUser.userToken
) {
    companion object {
        fun create(a: Any): GeneralParam {
            val json = Holder.appComponent.gson().toJson(a)
            val param = EncryptionHelper.encrypt(json)
            return GeneralParam(Param = param)
        }

        fun createForBasePostInfo(): GeneralParam {
            return create(a = BasePostInfo())
        }

        fun createForOrgParam(): GeneralParam {
            return create(a = OrgParam())
        }
    }
}