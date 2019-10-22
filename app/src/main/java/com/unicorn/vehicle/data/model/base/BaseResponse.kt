package com.unicorn.vehicle.data.model.base

import com.blankj.utilcode.util.ToastUtils

open class BaseResponse<T>(
    errorCode: String,
    val success: Boolean,
    val errorMsg: String,
    val data: T
) : ErrorCode(errorCode) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) ToastUtils.showShort(errorMsg)
            return failed
        }
}