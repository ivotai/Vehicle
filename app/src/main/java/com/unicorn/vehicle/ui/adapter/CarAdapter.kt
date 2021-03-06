package com.unicorn.vehicle.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.Param
import com.unicorn.vehicle.app.imgBaseUrl
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.ui.CarUsageLogListAct
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car.*

class CarAdapter : BaseQuickAdapter<Car, KVHolder>(R.layout.item_car) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: KVHolder, item: Car) {
        helper.apply {
            tvNo.text = item.no
            tvCarTypeDisplay.text = item.carTypeDisplay
            tvCarStateDisplay.text = "${item.carStateDisplay} ${item.requisitionUserName}"
            val url = imgBaseUrl + item.pictureUrl
            Glide.with(context).load(url).into(ivImage)

            Glide.with(context).load(if (item.carInGarage) R.mipmap.car else R.mipmap.car_grey)
                .into(ivCarInGarage)
            Glide.with(context).load(if (item.keyInBox) R.mipmap.key else R.mipmap.key_grey)
                .into(ivKeyInBox)

            root.safeClicks().subscribe {
                Intent(mContext, CarUsageLogListAct::class.java).apply {
                    putExtra(Param, item.id.toString())
                }.let { mContext.startActivity(it) }
            }
        }
    }

}