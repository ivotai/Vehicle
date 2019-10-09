package com.unicorn.vehicle.data.api

import com.unicorn.vehicle.data.model.CarRequisitionListParam
import com.unicorn.vehicle.data.model.LoggedUser
import com.unicorn.vehicle.data.model.UserLoginParam
import com.unicorn.vehicle.data.model.base.BaseResponse
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<BaseResponse<LoggedUser>>

    @POST("Car/CarRequisitionList")
    fun getCarRequisitionList(@Body pageRequest: PageRequest<CarRequisitionListParam>): Single<PageResponse<Any>>
//    @POST("Authorization/UserLogin")
//    fun autoLogin(@Body userLogin: UserLogin): Call<Response<UserLoginResult>>

}
