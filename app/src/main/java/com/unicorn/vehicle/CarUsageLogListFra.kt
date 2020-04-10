package com.unicorn.vehicle

import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.app.trimText
import com.unicorn.vehicle.data.model.CarUsageLog
import com.unicorn.vehicle.data.model.DictItem
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.param.CarUsageLogListParam
import com.unicorn.vehicle.ui.adapter.CarUsageLogAdapter
import com.unicorn.vehicle.ui.adapter.DictAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageFra
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_car_usage_log_list.*
import java.util.concurrent.TimeUnit

class CarUsageLogListFra : SimplePageFra<CarUsageLog, KVHolder>() {

    override fun initViews() {
        super.initViews()
        initDropDownView()
        mRecyclerView.addDefaultItemDecoration(1)
    }

    private fun initDropDownView() {
        val collapsedView =
            LayoutInflater.from(context).inflate(R.layout.view_car_usage_log_condition, null, false)
        dropDownView.setHeaderView(collapsedView)
        tvEventType = collapsedView.findViewById(R.id.tvEventType)
        tvStartTime = collapsedView.findViewById(R.id.tvStartTime)
        tvEndTime = collapsedView.findViewById(R.id.tvEndTime)
        etCarNo = collapsedView.findViewById(R.id.etCarNo)

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

        tvEventType.clicks().subscribe {
            if (dropDownView.isExpanded) {
                dropDownView.collapseDropDown()
                return@subscribe
            }
            api.getDictCarUsageEventType()
                .observeOnMain(this)
                .subscribeBy(
                    onSuccess = { response ->
                        if (response.failed) return@subscribeBy
                        dictAdapter.setNewData(response.data.plusElement(DictItem(null, "所有")))
                        dropDownView.expandDropDown()
                    }
                )
        }

        etCarNo.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadFirstPage() }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, DictItem::class.java, Consumer {
            tvEventType.text = it.value
            eventType = it.id
            if (it.id == null) tvEventType.text = "使用事件"
            dropDownView.collapseDropDown()
            loadFirstPage()
        })
    }

    private val dictAdapter = DictAdapter()

    //

    override val simpleAdapter = CarUsageLogAdapter()

    override fun loadPage(pageNo: Int): Single<PageResponse<CarUsageLog>> =
        api.getCarUsageLogList(
            PageRequest(
                pageNo = pageNo,
                searchParam = CarUsageLogListParam(
                    eventType = eventType,
                    startTime = startTime,
                    endTime = endTime,
                    carNo = etCarNo.trimText()
                )
            )
        )

    private var eventType: Int? = null

    private var startTime: String? = null

    private var endTime: String? = null

    private lateinit var tvEventType: TextView

    private lateinit var tvStartTime: TextView

    private lateinit var tvEndTime: TextView

    private lateinit var etCarNo: EditText

    override val mRecyclerView: RecyclerView
        get() = recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = swipeRefreshLayout

    override val layoutId = R.layout.fra_car_usage_log_list

}