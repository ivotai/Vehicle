package com.unicorn.vehicle.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.helper.DictHelper
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_car_requisition.*

class CarRequisitionAdapter :
    BaseQuickAdapter<CarRequisition, KVHolder>(R.layout.item_car_requisition) {

    override fun convert(helper: KVHolder, item: CarRequisition) {
        helper.apply {
            rtvRequisitionServerTime.text = item.requisitionServerTime
            tvRequisitionUserName.text = item.requisitionUserName
            tvRequisitionCause.text = item.requisitionCause
            tvRequisitionCarTypeDisplay.text = item.requisitionCarTypeDisplay
            tvIsState0.visibility = if (item.state == 0) View.VISIBLE else View.INVISIBLE
            tvRequisitionState.text =
                DictHelper.requisitionStates.findLast { it.id == item.state }?.value

//            root.safeClicks().subscribe {
//                Intent(mContext,ApplyDetailAct::class.java).apply {
//                    putExtra("Apply",item)
//                }.let { mContext.startActivity(it) }
//            }
        }
    }

}