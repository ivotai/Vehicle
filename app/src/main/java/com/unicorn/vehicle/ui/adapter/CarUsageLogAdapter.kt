package com.unicorn.vehicle.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.data.model.CarUsageLog
import com.unicorn.vehicle.ui.base.KVHolder

class CarUsageLogAdapter : BaseQuickAdapter<CarUsageLog, KVHolder>(R.layout.item_car) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: KVHolder, item: CarUsageLog) {

    }

}