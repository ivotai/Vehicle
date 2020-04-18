package com.unicorn.vehicle.ui.chart

import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import com.unicorn.vehicle.ui.adapter.pager.CarChartPagerAdapter

class Chart1NFra : BaseHorizontalBarChartFra() {

    override val title = CarChartPagerAdapter.titles[0]

    override val seriesName = "总使用次数（单位：次）"

    override fun getData(statisticCommonParam: StatisticCommonParam) =
        api.getUsageCountForCar(statisticCommonParam = statisticCommonParam)

}