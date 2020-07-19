package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.Param
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.app.helper.EncryptionHelper
import com.unicorn.vehicle.app.toBeanList
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.param.CarListParam
import com.unicorn.vehicle.data.model.param.GeneralParam
import com.unicorn.vehicle.ui.adapter.CarSelectAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import io.reactivex.Single
import kotlinx.android.synthetic.main.ui_swipe_recycler.*

// 查询特定 carType 的车辆
class CarSelectFra : SimplePageFra<Car, KVHolder>() {

    override fun initViews() {
        super.initViews()
        recyclerView.addDefaultItemDecoration(1)
    }

    override val simpleAdapter = CarSelectAdapter()

    override fun loadPage(pageNo: Int): Single<PageResponse<Car>> {
        val pageRequest = PageRequest(
            pageNo = pageNo,
            searchParam = CarListParam(carType = carType)
        )
        val generalParam = GeneralParam.create(pageRequest)
        return api.getCarList(generalParam).doOnSuccess {
            val json = EncryptionHelper.decrypt(it.encryptionData)
            it.items = json.toBeanList()
        }
    }

    private val carType by lazy { arguments!!.getInt(Param, 0) }

}