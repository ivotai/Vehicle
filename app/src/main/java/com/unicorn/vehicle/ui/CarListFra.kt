package com.unicorn.vehicle.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarListParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.ui.adapter.CarSelectAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import io.reactivex.Single
import kotlinx.android.synthetic.main.fra_car_list.*

class CarListFra : SimplePageFra<Car, KVHolder>() {

    override fun initViews() {
        titleBar.setTitle("用车状态")
        super.initViews()
        recyclerView.addDefaultItemDecoration(1)
    }

    override val simpleAdapter = CarSelectAdapter()

    override fun loadPage(page: Int): Single<PageResponse<Car>> =
        api.getCarList(
            PageRequest(
                pageNo = page, searchParam = CarListParam(
                    carType = carType,
                    carState = carState
                )
            )
        )

    private var carType: Int? = null

    private var carState: Int? = null

    override val layoutId = R.layout.fra_car_list

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

}