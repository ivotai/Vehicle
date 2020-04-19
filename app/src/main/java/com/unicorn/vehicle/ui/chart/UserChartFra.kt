package com.unicorn.vehicle.ui.chart

import com.unicorn.vehicle.data.model.param.StatisticCommonParam

class UserChartFra : BaseHorizontalBarChartFra2() {

    override val title = "个人用车总览"

    override val seriesName1 = "总用车次数（单位：次）"

    override val seriesName2 = "平均每次使用时长（单位：小时）"

    override fun getData1(statisticCommonParam: StatisticCommonParam) =
        api.getUsageCountForUser(statisticCommonParam)

    override fun getData2(statisticCommonParam: StatisticCommonParam) =
        api.getUsingHoursAverageForUser(statisticCommonParam)

}
