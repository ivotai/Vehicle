package com.unicorn.vehicle.data.model.base

import com.blankj.utilcode.util.ToastUtils

open class Response<T>(
    val success: Boolean,
    val errorMsg: String,
    var data: T,
    val errorCode: String,
    val encryptionData: String
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) ToastUtils.showShort(errorMsg)
            return failed
        }
}