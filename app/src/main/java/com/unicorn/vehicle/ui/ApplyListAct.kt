package com.unicorn.vehicle.ui

import com.unicorn.vehicle.data.model.Apply
import com.unicorn.vehicle.data.model.base.BaseResponse
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.ui.adapter.ApplyAdapter
import com.unicorn.vehicle.ui.base.KVHolder
import com.unicorn.vehicle.ui.base.SimplePageAct
import io.reactivex.Single

class ApplyListAct : SimplePageAct<Apply, KVHolder>() {

    override val simpleAdapter = ApplyAdapter()

    override fun loadPage(page: Int): Single<BaseResponse<PageResponse<Apply>>> {
        return Single.create {
            val content = ArrayList<Apply>()
            content.add(Apply("申请1"))
            val pageResponse = PageResponse(
                content = content,
                totalElements = 10
            )
            val baseResponse = BaseResponse(
                success = true,
                data = pageResponse,
                message = ""
            )
            it.onSuccess(baseResponse)
        }
    }

}