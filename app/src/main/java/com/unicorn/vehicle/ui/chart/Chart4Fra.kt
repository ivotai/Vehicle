package com.unicorn.vehicle.ui.chart

import com.unicorn.vehicle.data.model.StatisticCommonItem
import com.unicorn.vehicle.data.model.base.Response
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import io.reactivex.Observable

class Chart4Fra : BaseHorizontalBarChartFra() {

    override fun api(statisticCommonParam: StatisticCommonParam): Observable<Response<List<StatisticCommonItem>>> {
        return api.getUsageCountForCar(statisticCommonParam)
    }

}