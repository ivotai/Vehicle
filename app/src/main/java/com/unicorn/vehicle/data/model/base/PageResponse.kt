package com.unicorn.vehicle.data.model.base

import com.blankj.utilcode.util.ToastUtils

data class PageResponse<T>(
    val success: Boolean,
    val errorMsg: String,
    val errorCode: String,
    val pageNo: Int,
    val pageSize: Int,
    val total: Int,          // 总数
    val totalPage: Int,      // 总页数
    val items: List<T>
) {
    val failed: Boolean
        get() {
            val failed = !success
            if (failed) ToastUtils.showShort(errorMsg)
            return failed
        }
}