package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarListParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.ui.adapter.CarAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageAct
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.ui_title_swipe_recycler.*

class CarListAct : SimplePageAct<Car, KVHolder>() {

    override fun initViews() {
        super.initViews()
        titleBar.setTitle("选择车辆")
        recyclerView.addDefaultItemDecoration(1)
    }

    override val simpleAdapter = CarAdapter()

    override fun loadPage(page: Int): Single<PageResponse<Car>> =
        api.getCarList(PageRequest(pageNo = page, searchParam = CarListParam(carType = carType)))

    override fun registerEvent() {
        RxBus.registerEvent(this, Car::class.java, Consumer {
            finish()
        })
    }

    private val carType by lazy { intent.getIntExtra(Key.CarType, 0) }

}