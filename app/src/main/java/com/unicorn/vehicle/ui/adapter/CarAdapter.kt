package com.unicorn.vehicle.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car.*

class CarAdapter : BaseQuickAdapter<Car, KVHolder>(R.layout.item_car) {

    override fun convert(helper: KVHolder, item: Car) {
        helper.apply {
            tvName.text = "${item.no}"
            tvNo.text = item.carTypeDisplay
            tvCarStateDisplay.text = item.carStateDisplay
            Glide.with(context).load("https://img.mychebao.com/download/image/2b87963f872a2b7d7a2cb4846f88ad22_mid.jpg").into(ivImage)

            root.safeClicks().subscribe {
                RxBus.post(item)
            }
        }
    }

}