package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.helper.EncryptionHelper
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.event.CarRequisitionTotal
import com.unicorn.vehicle.data.model.event.RefreshCarRequisitionList
import com.unicorn.vehicle.data.model.param.CarRequisitionListParam
import com.unicorn.vehicle.data.model.param.GeneralParam
import com.unicorn.vehicle.ui.adapter.CarRequisitionAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import com.unicorn.vehicle.ui.other.LinearSpanDecoration
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.ui_swipe_recycler.*


class CarRequisitionListFra : SimplePageFra<CarRequisition, KVHolder>() {

    override val grayBg = true

    override fun initViews() {
        super.initViews()
        recyclerView.addItemDecoration(LinearSpanDecoration(defaultPadding))
    }

    override fun afterLoadFirstPage(total: Int) {
        RxBus.post(CarRequisitionTotal(position = position, total = total))
    }

    override val simpleAdapter = CarRequisitionAdapter()

    override fun loadPage(pageNo: Int): Single<PageResponse<CarRequisition>> {
        val pageRequest = PageRequest(
            pageNo = pageNo,
            searchParam =
            if (position == 0)
                CarRequisitionListParam(
                    states = listOf(0)
                )
            else
                CarRequisitionListParam(
                    states = listOf(1, 2, 3, 4),
                    approvalUserID = uid
                )
        )
        val generalParam = GeneralParam.create(pageRequest)
        return api.getCarRequisitionList(generalParam).doOnSuccess {
            val json = EncryptionHelper.decrypt(it.encryptionData)
            it.items = json.toBeanList()
        }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, RefreshCarRequisitionList::class.java, Consumer {
            loadFirstPage()
        })
    }

    private val position by lazy { arguments!!.getInt(Param) }

}