package com.unicorn.vehicle.ui.chart

import com.unicorn.vehicle.data.model.param.StatisticCommonParam

class Chart1Fra : BaseHorizontalBarChartFra() {

    override val title = "总使用次数"

    override val seriesName = "总使用次数（单位：次）"

    override fun getData(statisticCommonParam: StatisticCommonParam) =
        api.getUsageCountForCar(statisticCommonParam)

    override val titleVisible = false

}