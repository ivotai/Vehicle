package com.unicorn.vehicle.ui.chart

import com.unicorn.vehicle.data.model.param.StatisticCommonParam

class Chart2Fra : BaseHorizontalBarChartFra() {

    override val title = "平均每日使用时长"

    override val seriesName = "平均每日使用时长（单位：小时）"

    override fun getData(statisticCommonParam: StatisticCommonParam) =
        api.getUsingHoursAverageForCar(statisticCommonParam = statisticCommonParam)

    override val useIntValueFormatter = false

    override val titleVisible = false

}