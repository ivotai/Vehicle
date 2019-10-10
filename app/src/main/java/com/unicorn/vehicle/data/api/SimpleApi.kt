package com.unicorn.vehicle.data.api

import com.unicorn.vehicle.data.model.*
import com.unicorn.vehicle.data.model.base.BaseResponse
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<BaseResponse<LoggedUser>>

    @POST("Car/CarRequisitionList")
    fun getCarRequisitionList(@Body pageRequest: PageRequest<CarRequisitionListParam>): Single<PageResponse<CarRequisition>>

    @POST("Car/CarList")
    fun getCarList(@Body pageRequest: PageRequest<CarListParam>): Single<PageResponse<Car>>

    @POST("Car/CarRequisitionApprove")
    fun approve(@Body carRequisition: CarRequisition): Single<BaseResponse<CarRequisition>>

    @POST("Car/CarRequisitionDeny")
    fun deny(@Body carRequisition: CarRequisition): Single<BaseResponse<CarRequisition>>
//    @POST("Authorization/UserLogin")
//    fun autoLogin(@Body userLogin: UserLogin): Call<Response<UserLoginResult>>

    @POST("Code/DictCarState")
    fun getCarState(): Observable<BaseResponse<List<DictItem>>>

    @POST("Code/DictCarType")
    fun getCarType(): Observable<BaseResponse<List<DictItem>>>

    @POST("Code/DictRequisitionState")
    fun getDictRequisitionState(): Observable<BaseResponse<List<DictItem>>>

    @POST("Code/DictRequisitionFromType")
    fun getDictRequisitionFromType(): Observable<BaseResponse<List<DictItem>>>

}
