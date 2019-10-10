package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.Configs
import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.data.model.CarRequisitionListParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.event.CarRequisitionTotal
import com.unicorn.vehicle.data.model.event.RefreshCarRequisitionList
import com.unicorn.vehicle.ui.adapter.CarRequisitionAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import com.unicorn.vehicle.ui.other.LinearSpanDecoration
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.ui_swipe_recycler.*

class CarRequisitionListFra : SimplePageFra<CarRequisition, KVHolder>() {

    override fun initViews() {
        super.initViews()
        recyclerView.addItemDecoration(LinearSpanDecoration(Configs.defaultPaddingDp))
    }

    override fun afterLoadFirstPage(total: Int) {
        RxBus.post(CarRequisitionTotal(carRequisitionState = carRequisitionState, total = total))
    }

    override val simpleAdapter = CarRequisitionAdapter()

    override fun loadPage(page: Int): Single<PageResponse<CarRequisition>> =
        api.getCarRequisitionList(
            PageRequest(
                pageNo = page,
                searchParam = CarRequisitionListParam(state = carRequisitionState)
            )
        )

    override fun registerEvent() {
        RxBus.registerEvent(this, RefreshCarRequisitionList::class.java, Consumer {
            if (carRequisitionState == it.carRequisitionState)
                loadFirstPage()
        })
    }

    private val carRequisitionState by lazy { arguments!!.getInt(Key.CarRequisitionState) }

}