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
import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.helper.EncryptionHelper
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.DictItem
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.event.DictItemEvent
import com.unicorn.vehicle.data.model.param.CarListParam
import com.unicorn.vehicle.data.model.param.GeneralParam
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
        etNo = collapsedView.findViewById(R.id.etNo)

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
            api.getDictCarType()
                .doOnSuccess {
                    val json = EncryptionHelper.decrypt(it.encryptionData)
                    it.data = json.toBeanList()
                }
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
            api.getDictCarState()
                .doOnSuccess {
                    val json = EncryptionHelper.decrypt(it.encryptionData)
                    it.data = json.toBeanList()
                }
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
        RxBus.registerEvent(this, DictItemEvent::class.java, Consumer {
            if (it.key != javaClass.name) return@Consumer
            val dictItem = it.dictItem
            if (isCarState) {
                tvCarState.text = dictItem.value
                carState = dictItem.id
                if (dictItem.id == null) tvCarState.text = "车辆状态"
            } else {
                tvCarType.text = dictItem.value
                carType = dictItem.id
                if (dictItem.id == null) tvCarType.text = "车辆类型"
            }
            dropDownView.collapseDropDown()
            loadFirstPage()
        })
    }

    private val dictAdapter = DictAdapter(javaClass.name)

    //

    private var isCarState = true

    //

    override val simpleAdapter = CarAdapter()

    override fun loadPage(pageNo: Int): Single<PageResponse<Car>> {
        val pageRequest = PageRequest(
            pageNo = pageNo,
            searchParam = CarListParam(
                carType = carType,
                carState = carState,
                no = etNo.trimText()
            )
        )
        val generalParam = GeneralParam.create(pageRequest)
        return api.getCarList(generalParam).doOnSuccess {
            val json = EncryptionHelper.decrypt(it.encryptionData)
            it.items = json.toBeanList()
        }
    }

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