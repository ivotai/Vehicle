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
        fun create(basePostInfo: BasePostInfo): GeneralParam {
            // 确保从服务获取的数据也被填充 uid
            if (basePostInfo.idCardNumber == null) basePostInfo.idCardNumber = loggedUser.uid
            val json = Holder.appComponent.gson().toJson(basePostInfo)
            val param = EncryptionHelper.encrypt(json)
            return GeneralParam(Param = param)
        }

        fun createForBasePostInfo(): GeneralParam {
            return create(basePostInfo = BasePostInfo())
        }

        fun createForOrgParam(): GeneralParam {
            return create(basePostInfo = OrgParam())
        }
    }
}