package com.unicorn.vehicle.data.model.base

import com.unicorn.vehicle.app.defaultPageSize

data class PageRequest<T>(
    val pageNo: Int,        //页码，从1开始
    val pageSize: Int = defaultPageSize,
    val searchParam: T
)