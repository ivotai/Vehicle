package com.unicorn.vehicle.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.data.model.Apply
import com.unicorn.vehicle.ui.base.KVHolder

class ApplyAdapter : BaseQuickAdapter<Apply, KVHolder>(R.layout.item_apply) {

    override fun convert(helper: KVHolder, item: Apply) {
        helper.apply {
        }
    }

}