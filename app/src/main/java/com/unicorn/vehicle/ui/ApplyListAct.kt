//package com.unicorn.vehicle.ui
//
//import com.unicorn.vehicle.app.Configs
//import com.unicorn.vehicle.data.model.Apply
//import com.unicorn.vehicle.data.model.base.BaseResponse
//import com.unicorn.vehicle.data.model.base.PageResponse
//import com.unicorn.vehicle.ui.adapter.ApplyAdapter
//import com.unicorn.vehicle.ui.base.KVHolder
//import com.unicorn.vehicle.ui.base.SimplePageAct
//import com.unicorn.vehicle.ui.other.LinearSpanDecoration
//import io.reactivex.Single
//import kotlinx.android.synthetic.main.ui_title_swipe_recycler.*
//
//class ApplyListAct : SimplePageAct<Apply, KVHolder>() {
//
//    override fun initViews() {
//        super.initViews()
//        titleBar.setTitle("用车申请")
//        recyclerView.addItemDecoration(LinearSpanDecoration(Configs.defaultPaddingDp))
//    }
//
//    override val simpleAdapter = ApplyAdapter()
//
//    override fun loadPage(page: Int): Single<PageResponse<Apply>> {
//        return Single.create {
//            val content = ArrayList<Apply>().apply {
//                for (i in 1..10)
//                    add(
//                        Apply(
//                            "秦萱熙 ",
//                            "出差",
//                            "2019-10-08",
//                            "2019-10-11",
//                            "杭州",
//                            "K33"
//                        )
//                    )
//            }
//            val pageResponse = PageResponse(
//                content = content,
//                totalElements = 10
//            )
//            val baseResponse = BaseResponse(
//                success = true,
//                data = pageResponse,
//                errorMsg = "",
//                errorCode = ""
//            )
//            it.onSuccess(baseResponse)
//        }
//    }
//
//}