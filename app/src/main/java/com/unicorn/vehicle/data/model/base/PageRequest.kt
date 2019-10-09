package com.unicorn.vehicle.data.model.base

import com.unicorn.vehicle.app.Configs

data class PageRequest<T>(
    val pageNo: Int,        //页码，从1开始
    val pageSize: Int = Configs.defaultPageSize,
    val searchParam: T
)