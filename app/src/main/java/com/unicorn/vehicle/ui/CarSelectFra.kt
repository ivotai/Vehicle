package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarListParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
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

    override fun loadPage(page: Int): Single<PageResponse<Car>> =
        api.getCarList(PageRequest(pageNo = page, searchParam = CarListParam(carType = carType)))

    private val carType by lazy { arguments!!.getInt(Key.CarType, 0) }

}