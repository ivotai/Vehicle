package com.unicorn.vehicle.ui.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.data.model.CarUsageLog
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car_usage_log.*

class CarUsageLogAdapter : BaseQuickAdapter<CarUsageLog, KVHolder>(R.layout.item_car_usage_log) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: KVHolder, item: CarUsageLog) {
        helper.apply {
            tvCarNo.text = item.carNo
            tvEventType.text = item.eventTypeDisplay
            tvEventType.setTextColor(item.eventTypeTextColor)
            Glide.with(mContext).load(item.eventTypeResId).into(ivEventType)
            tvUserName.text = item.userName
            tvServerTime.text = item.serverTime
        }
    }

}