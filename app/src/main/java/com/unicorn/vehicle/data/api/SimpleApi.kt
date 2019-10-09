package com.unicorn.vehicle.data.api

import com.unicorn.vehicle.data.model.LoggedUser
import com.unicorn.vehicle.data.model.UserLoginParam
import com.unicorn.vehicle.data.model.base.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<BaseResponse<LoggedUser>>

//    @POST("Authorization/UserLogin")
//    fun autoLogin(@Body userLogin: UserLogin): Call<Response<UserLoginResult>>

}
