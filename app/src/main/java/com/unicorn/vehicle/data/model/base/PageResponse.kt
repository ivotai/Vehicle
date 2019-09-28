package com.unicorn.vehicle.data.model.base

data class PageResponse<T>(
    val content: List<T>,
    val totalElements: Int
)