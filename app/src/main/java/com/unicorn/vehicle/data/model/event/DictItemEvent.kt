package com.unicorn.vehicle.data.model.event

import com.unicorn.vehicle.data.model.DictItem

data class DictItemEvent(
    val dictItem: DictItem,
    val key: String
)