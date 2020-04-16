package com.unicorn.vehicle.data.model.param

import com.unicorn.vehicle.app.displayDateFormat
import org.joda.time.DateTime

data class StatisticCommonParam(
    var dateStart: String = DateTime().minusMonths(1).toString(displayDateFormat),
    var dateEnd: String = DateTime().toString(displayDateFormat)
)

