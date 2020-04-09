package com.unicorn.vehicle.data.model.base

import defaultPageSize

data class PageRequest<T>(
    val pageNo: Int,        //页码，从1开始
    val pageSize: Int = defaultPageSize,
    val searchParam: T
)