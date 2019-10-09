package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.Configs
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.data.model.CarRequisitionListParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.event.Approve
import com.unicorn.vehicle.ui.adapter.CarRequisitionAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageAct
import com.unicorn.vehicle.ui.other.LinearSpanDecoration
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.ui_title_swipe_recycler.*

class CarRequisitionListAct : SimplePageAct<CarRequisition, KVHolder>() {

    override fun initViews() {
        super.initViews()
        titleBar.setTitle("用车申请")
        recyclerView.addItemDecoration(LinearSpanDecoration(Configs.defaultPaddingDp))
    }

    override val simpleAdapter = CarRequisitionAdapter()

    override fun loadPage(page: Int): Single<PageResponse<CarRequisition>> =
        api.getCarRequisitionList(
            PageRequest(
                pageNo = page,
                searchParam = CarRequisitionListParam()
            )
        )

    override fun registerEvent() {
        RxBus.registerEvent(this, Approve::class.java, Consumer {
            loadFirstPage()
        })
    }

}