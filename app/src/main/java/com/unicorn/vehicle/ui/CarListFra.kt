package com.unicorn.vehicle.ui

import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.app.trimText
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.DictItem
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.param.CarListParam
import com.unicorn.vehicle.ui.adapter.CarAdapter
import com.unicorn.vehicle.ui.adapter.DictAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_car_list.*
import java.util.concurrent.TimeUnit

class CarListFra : SimplePageFra<Car, KVHolder>() {

    override fun initViews() {
        super.initViews()
        initDropDownView()
        mRecyclerView.addDefaultItemDecoration(1)
    }

    private fun initDropDownView() {
        val collapsedView =
            LayoutInflater.from(context).inflate(R.layout.view_car_condition, null, false)
        dropDownView.setHeaderView(collapsedView)
        tvCarState = collapsedView.findViewById(R.id.tvCarState)
        tvCarType = collapsedView.findViewById(R.id.tvCarType)
        etNo = collapsedView.findViewById(R.id.etKeyword)

        val expandedView =
            LayoutInflater.from(context).inflate(R.layout.ui_recycler, null, false)
        dropDownView.setExpandedView(expandedView)

        expandedView.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dictAdapter
            addDefaultItemDecoration(1)
        }
    }

    override fun bindIntent() {
        super.bindIntent()

        tvCarType.clicks().subscribe {
            if (dropDownView.isExpanded) {
                dropDownView.collapseDropDown()
                return@subscribe
            }
            isCarState = false
            api.getCarType()
                .observeOnMain(this)
                .subscribeBy(
                    onSuccess = { response ->
                        if (response.failed) return@subscribeBy
                        dictAdapter.setNewData(response.data.plusElement(DictItem(null, "所有")))
                        dropDownView.expandDropDown()
                    }
                )
        }

        tvCarState.clicks().subscribe {
            if (dropDownView.isExpanded) {
                dropDownView.collapseDropDown()
                return@subscribe
            }
            isCarState = true
            api.getCarState()
                .observeOnMain(this)
                .subscribeBy(
                    onSuccess = { response ->
                        if (response.failed) return@subscribeBy
                        dictAdapter.setNewData(response.data.plusElement(DictItem(null, "所有")))
                        dropDownView.expandDropDown()
                    }
                )
        }

        etNo.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadFirstPage() }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, DictItem::class.java, Consumer {
            if (isCarState) {
                tvCarState.text = it.value
                carState = it.id
                if (it.id == null) tvCarState.text = "车辆状态"
            } else {
                tvCarType.text = it.value
                carType = it.id
                if (it.id == null) tvCarType.text = "车辆类型"
            }
            dropDownView.collapseDropDown()
            loadFirstPage()
        })
    }

    private val dictAdapter = DictAdapter()

    //

    private var isCarState = true

    //

    override val simpleAdapter = CarAdapter()

    override fun loadPage(pageNo: Int): Single<PageResponse<Car>> =
        api.getCarList(
            PageRequest(
                pageNo = pageNo,
                searchParam = CarListParam(
                    carType = carType,
                    carState = carState,
                    no = etNo.trimText()
                )
            )
        )

    private var carType: Int? = null

    private var carState: Int? = null

    private lateinit var tvCarType: TextView

    private lateinit var tvCarState: TextView

    private lateinit var etNo: EditText

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override val layoutId = R.layout.fra_car_list

}