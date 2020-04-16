package com.unicorn.vehicle.data.model.param

import com.unicorn.vehicle.app.displayDateFormat
import org.joda.time.DateTime

data class StatisticCommonParam(
    val dateStart: String = DateTime().minusMonths(1).toString(displayDateFormat),
    val dateEnd: String = DateTime().toString(displayDateFormat)
)

