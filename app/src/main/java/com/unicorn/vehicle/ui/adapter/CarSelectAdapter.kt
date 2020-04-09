package com.unicorn.vehicle.ui.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.ui.base.KVHolder
import imgBaseUrl
import kotlinx.android.synthetic.main.item_car.*

class CarSelectAdapter : BaseQuickAdapter<Car, KVHolder>(R.layout.item_car) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: KVHolder, item: Car) {
        helper.apply {
            tvName.text = item.no
            tvNo.text = item.carTypeDisplay
            tvCarStateDisplay.text = "${item.carStateDisplay} ${item.requisitionUserName}"
//            Glide.with(context).load("https://img.mychebao.com/download/image/2b87963f872a2b7d7a2cb4846f88ad22_mid.jpg").into(ivImage)
            val url = imgBaseUrl + item.pictureUrl
            Glide.with(context).load(url).into(ivImage)

//            val bg1 = GradientDrawable().apply {
//                setStroke(1, Color.parseColor("#6D72D0"))
//            }
//            ivCarInGarage.background = bg1
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