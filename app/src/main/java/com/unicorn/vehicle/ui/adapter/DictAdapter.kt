package com.unicorn.vehicle.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.unicorn.vehicle.R
import com.unicorn.vehicle.data.model.DictItem
import com.unicorn.vehicle.ui.base.KVHolder
import kotlinx.android.synthetic.main.item_dict.*

class DictAdapter : BaseQuickAdapter<DictItem, KVHolder>(R.layout.item_dict) {

    override fun convert(helper: KVHolder, item: DictItem) {
        helper.apply {
            tvValue.text = item.value
        }
    }

}