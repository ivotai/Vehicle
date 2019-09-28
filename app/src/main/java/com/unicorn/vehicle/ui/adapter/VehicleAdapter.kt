package com.unicorn.vehicle.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.Vehicle
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_vehicle.*

class VehicleAdapter : BaseQuickAdapter<Vehicle, KVHolder>(R.layout.item_vehicle) {

    override fun convert(helper: KVHolder, item: Vehicle) {
        helper.apply {
            tvName.text = item.name
            Glide.with(context).load(item.img).into(ivImage)
            tvYear.text = item.year
            tvPrice.text = item.price

            root.safeClicks().subscribe {
                RxBus.post(item)
            }
        }
    }

}