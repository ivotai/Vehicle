package com.unicorn.vehicle.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.ui.base.KVHolder

class CarRequisitionAdapter :
    BaseQuickAdapter<CarRequisition, KVHolder>(R.layout.item_car_requisition) {

    override fun convert(helper: KVHolder, item: CarRequisition) {
        helper.apply {
            //            tvPerson.text = item.person
//            tvReason.text = item.reason
//            tvTime.text = "${item.startTime} 至 ${item.endTime}"
//            tvDestination.text = item.destination

//            root.safeClicks().subscribe {
//                Intent(mContext,ApplyDetailAct::class.java).apply {
//                    putExtra("Apply",item)
//                }.let { mContext.startActivity(it) }
//            }
        }
    }

}