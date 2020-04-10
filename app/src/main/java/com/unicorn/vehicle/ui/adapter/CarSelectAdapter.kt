package com.unicorn.vehicle.ui.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.BuildConfig
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car.*

class CarSelectAdapter : BaseQuickAdapter<Car, KVHolder>(R.layout.item_car) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: KVHolder, item: Car) {
        helper.apply {
            tvName.text = item.no
            tvNo.text = item.carTypeDisplay
            tvCarStateDisplay.text = "${item.carStateDisplay} ${item.requisitionUserName}"
            val url = BuildConfig.IMG_BASE_URL + item.pictureUrl
            Glide.with(context).load(url).into(ivImage)

            Glide.with(context).load(if (item.carInGarage) R.mipmap.car else R.mipmap.car_grey)
                .into(ivCarInGarage)
            Glide.with(context).load(if (item.keyInBox) R.mipmap.key else R.mipmap.key_grey)
                .into(ivKeyInBox)

            root.safeClicks().subscribe {
                RxBus.post(item)
            }
        }
    }

}