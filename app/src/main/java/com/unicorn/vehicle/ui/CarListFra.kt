package com.unicorn.vehicle.ui

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anthonyfdev.dropdownview.DropDownView
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.helper.DictHelper
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarListParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.ui.adapter.CarSelectAdapter
import com.unicorn.vehicle.ui.adapter.DictAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import io.reactivex.Single
import kotlinx.android.synthetic.main.fra_car_list.*

class CarListFra : SimplePageFra<Car, KVHolder>() {

    override fun initViews() {
        super.initViews()
//        mRecyclerView.addDefaultItemDecoration(1)
        s()
    }

    private val dictAdapter = DictAdapter()
    private fun s(){
        val collapsedView = LayoutInflater.from(context).inflate(R.layout.view_my_drop_down_header, null, false)
        dropDownView.setHeaderView(collapsedView)

       val expandedView =
            LayoutInflater.from(context).inflate(R.layout.view_my_drop_down_expanded, null, false)
        dropDownView.setExpandedView(expandedView)
        rvDict = expandedView.findViewById(R.id.recyclerView)
        rvDict.layoutManager = LinearLayoutManager(context)
        rvDict.adapter = dictAdapter

        dropDownView.dropDownListener = object :DropDownView.DropDownListener{
            /**
             * This method will only be triggered when [.collapseDropDown] is called successfully.
             */
            override fun onCollapseDropDown() {
            }

            /**
             * This method will only be triggered when [.expandDropDown] is called successfully.
             */
            override fun onExpandDropDown() {
                onHeadChange()
            }
        }

    }
    lateinit var rvDict: RecyclerView


    var isState = true

    private fun onHeadChange(){
        isState = true
        dictAdapter.setNewData(DictHelper.carStates)
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

    //

    override val layoutId = R.layout.fra_car_list

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

}