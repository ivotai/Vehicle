package com.unicorn.vehicle.data.model.base

import com.blankj.utilcode.util.ToastUtils

open class BaseResponse<T>(
    val success: Boolean,
    val errorMsg: String,
    val errorCode: String,
    val data: T
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) ToastUtils.showShort(errorMsg)
            return failed
        }
}