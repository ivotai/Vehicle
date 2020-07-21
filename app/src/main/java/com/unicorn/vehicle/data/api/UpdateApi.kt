package com.unicorn.vehicle.data.api

import com.unicorn.vehicle.data.model.*
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.base.Response
import com.unicorn.vehicle.data.model.param.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface UpdateApi {

    @POST("api/file/CheckAppVersion")
    fun checkAppVersion(@Body stringQuery: StringQuery): Single<Response<CheckAppVersionResponse>>

}
