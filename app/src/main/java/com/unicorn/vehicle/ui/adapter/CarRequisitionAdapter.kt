package com.unicorn.vehicle.ui.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.ui.CarRequisitionDetailAct
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car_requisition.*

class CarRequisitionAdapter :
    BaseQuickAdapter<CarRequisition, KVHolder>(R.layout.item_car_requisition) {

    override fun convert(helper: KVHolder, item: CarRequisition) {
        helper.apply {
            rtvRequisitionServerTime.text = item.requisitionServerTime
            tvRequisitionUserName.text = item.requisitionUserName
            tvRequisitionCauseDisplay.text = item.requisitionCauseDisplay
            tvRequisitionCarTypeDisplay.text = item.requisitionCarTypeDisplay
            tvRequisitionDestination.text = item.requisitionDestination
            tvIsState0.visibility = if (item.state == 0) View.VISIBLE else View.INVISIBLE

            root.safeClicks().subscribe {
                Intent(mContext, CarRequisitionDetailAct::class.java).apply {
                    putExtra(Key.CarRequisition, item)
                }.let { mContext.startActivity(it) }
            }
        }
    }

}