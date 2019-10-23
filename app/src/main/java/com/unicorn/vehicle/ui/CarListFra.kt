package com.unicorn.vehicle.ui

import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding3.view.clicks
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.app.helper.DictHelper
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarListParam
import com.unicorn.vehicle.data.model.DictItem
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.ui.adapter.CarSelectAdapter
import com.unicorn.vehicle.ui.adapter.DictAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fra_car_list.*

class CarListFra : SimplePageFra<Car, KVHolder>() {

    override fun initViews() {
        super.initViews()
        initDropDownView()
    }

    private fun initDropDownView() {
        val collapsedView =
            LayoutInflater.from(context).inflate(R.layout.view_my_drop_down_header, null, false)
        dropDownView.setHeaderView(collapsedView)
        tvCarState = collapsedView.findViewById(R.id.tvCarState)
        tvCarType = collapsedView.findViewById(R.id.tvCarType)

        val expandedView =
            LayoutInflater.from(context).inflate(R.layout.view_my_drop_down_expanded, null, false)
        dropDownView.setExpandedView(expandedView)

        val rvDict = expandedView.findViewById<RecyclerView>(R.id.recyclerView)
        rvDict.layoutManager = LinearLayoutManager(context)
        rvDict.adapter = dictAdapter
        rvDict.addDefaultItemDecoration(1)
    }

    override fun bindIntent() {
        super.bindIntent()
        tvCarType.clicks().subscribe {
            if (dropDownView.isExpanded) {
                dropDownView.collapseDropDown()
                return@subscribe
            }
            isCarState = false
            dictAdapter.setNewData(DictHelper.carTypes.plusElement(DictItem(null, "所有")))
            dropDownView.expandDropDown()
        }
        tvCarState.clicks().subscribe {
            if (dropDownView.isExpanded) {
                dropDownView.collapseDropDown()
                return@subscribe
            }
            isCarState = true
            dictAdapter.setNewData(DictHelper.carStates.plusElement(DictItem(null, "所有")))
            dropDownView.expandDropDown()
        }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, DictItem::class.java, Consumer {
            if (isCarState) {
                tvCarState.text = it.value
                carState = it.id
                if(it.id == null) tvCarState.text = "车辆状态"
            } else {
                tvCarType.text = it.value
                carType = it.id
                if(it.id == null) tvCarType.text = "车辆类型"
            }
            loadFirstPage()
            dropDownView.collapseDropDown()
        })
    }

    private val dictAdapter = DictAdapter()

    //

    private var isCarState = true

    private lateinit var tvCarType: TextView

    private lateinit var tvCarState: TextView

    private var carType: Int? = null

    private var carState: Int? = null

    //

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

    override val layoutId = R.layout.fra_car_list

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

}