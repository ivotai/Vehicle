package com.unicorn.vehicle.ui

import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.displayDateFormat
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_charts.*
import org.joda.time.DateTime

class ChartsFra : BaseFra() {

    override fun initViews() {

    }

    private fun initChart1(){
        with(chart1){
            setDrawBarShadow(true)
           setDrawValueAboveBar(true)
            getDescription().setEnabled(true)

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
//            chart.setMaxVisibleValueCount(60)

            // scaling can now only be done on x- and y-axis separately

            // scaling can now only be done on x- and y-axis separately
            setPinchZoom(true)

            setDrawGridBackground(true)
            // chart.setDrawYLabels(false);
        }
    }

    override fun bindIntent() {
        getRequisitionCountForCar()
    }

    private fun getRequisitionCountForCar() {
        api.getRequisitionCountForCar(
            StatisticCommonParam(
                dateStart = dateStart,
                dateEnd = dateEnd
            )
        )
            .observeOnMain(this)
            .subscribe {
                it
            }
    }

    private var dateStart = DateTime().minusMonths(1).toString(displayDateFormat)

    private var dateEnd = DateTime().toString(displayDateFormat)

    override val layoutId = R.layout.fra_charts

}