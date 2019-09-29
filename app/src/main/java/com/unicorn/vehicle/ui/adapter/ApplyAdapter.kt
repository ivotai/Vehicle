package com.unicorn.vehicle.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.data.model.Apply
import com.unicorn.vehicle.ui.ApplyDetailAct
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_apply.*

class ApplyAdapter : BaseQuickAdapter<Apply, KVHolder>(R.layout.item_apply) {

    override fun convert(helper: KVHolder, item: Apply) {
        helper.apply {
            tvPerson.text = item.person
            tvReason.text = item.reason
            tvTime.text = "${item.startTime} 至 ${item.endTime}"
            tvDestination.text = item.destination

            root.safeClicks().subscribe {
                Intent(mContext,ApplyDetailAct::class.java).apply {
                    putExtra("Apply",item)
                }.let { mContext.startActivity(it) }
            }
        }
    }

}