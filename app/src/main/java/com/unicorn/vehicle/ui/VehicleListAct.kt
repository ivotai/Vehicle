package com.unicorn.vehicle.ui

import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.addDefaultItemDecoration
import com.unicorn.vehicle.data.model.Vehicle
import com.unicorn.vehicle.data.model.base.BaseResponse
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.ui.adapter.VehicleAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageAct
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.ui_title_swipe_recycler.*

class VehicleListAct : SimplePageAct<Vehicle, KVHolder>() {

    override fun initViews() {
        super.initViews()
        titleBar.setTitle("选择车辆")
        recyclerView.addDefaultItemDecoration(1)
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, Vehicle::class.java, Consumer {
            finish()
        })
    }

    override val simpleAdapter = VehicleAdapter()

    override fun loadPage(page: Int): Single<BaseResponse<PageResponse<Vehicle>>> {
        return Single.create {
            val content = ArrayList<Vehicle>().apply {
                add(
                    Vehicle(
                        "吉利汽车帝豪2018款",
                        "https://img.mychebao.com/download/image/0c5e781d5799fb8f7a2cb4846f88ad22_mid.jpg",
                        "2018-05-15",
                        "102.38万"
                    )
                )
                add(
                    Vehicle(
                        "马自达CX-52013款",
                        "https://img.mychebao.com/download/image/0e6d31e2a998fdd57a2cb4846f88ad22_mid.jpg",
                        "2014-06-30",
                        "10.94万"
                    )
                )
                add(
                    Vehicle(
                        "长安悦翔2012款",
                        "https://img.mychebao.com/download/image/a1e53f758077cfbb7a2cb4846f88ad22_mid.jpg",
                        "2013-06-19",
                        "2.72万"

                    )
                )
                add(
                    Vehicle(
                        "别克君威2005款",
                        "https://img.mychebao.com/download/image/808dec6b00c74769d92a7964fd4fd0ba_mid.jpg",
                        "2005-06-17",
                        "1.72万"
                    )
                )
                add(
                    Vehicle(
                        "奔驰C级2013款",
                        "https://img.mychebao.com/download/image/2b87963f872a2b7d7a2cb4846f88ad22_mid.jpg",
                        "2013-12-02",
                        "11.34万"
                    )
                )
                add(
                    Vehicle(
                        "吉利汽车帝豪2018款",
                        "https://img.mychebao.com/download/image/0c5e781d5799fb8f7a2cb4846f88ad22_mid.jpg",
                        "2018-05-15",
                        "102.38万"
                    )
                )
                add(
                    Vehicle(
                        "马自达CX-52013款",
                        "https://img.mychebao.com/download/image/0e6d31e2a998fdd57a2cb4846f88ad22_mid.jpg",
                        "2014-06-30",
                        "10.94万"
                    )
                )
                add(
                    Vehicle(
                        "长安悦翔2012款",
                        "https://img.mychebao.com/download/image/a1e53f758077cfbb7a2cb4846f88ad22_mid.jpg",
                        "2013-06-19",
                        "2.72万"

                    )
                )
                add(
                    Vehicle(
                        "别克君威2005款",
                        "https://img.mychebao.com/download/image/808dec6b00c74769d92a7964fd4fd0ba_mid.jpg",
                        "2005-06-17",
                        "1.72万"
                    )
                )
                add(
                    Vehicle(
                        "奔驰C级2013款",
                        "https://img.mychebao.com/download/image/2b87963f872a2b7d7a2cb4846f88ad22_mid.jpg",
                        "2013-12-02",
                        "11.34万"
                    )
                )
            }
            val pageResponse = PageResponse(
                content = content,
                totalElements = 10
            )
            val baseResponse = BaseResponse(
                success = true,
                data = pageResponse,
                errorMsg = "",
                errorCode = ""
            )
            it.onSuccess(baseResponse)
        }
    }

}