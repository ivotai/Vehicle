package com.unicorn.vehicle.data.api

import com.unicorn.vehicle.data.model.*
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.base.Response
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<Response<LoggedUser>>

    @POST("Authorization/UserLogin")
    fun autoLogin(@Body userLoginParam: UserLoginParam): Call<Response<LoggedUser>>

    @POST("Car/CarRequisitionList")
    fun getCarRequisitionList(@Body pageRequest: PageRequest<CarRequisitionListParam>): Single<PageResponse<CarRequisition>>

    @POST("Car/CarRequisitionInfo")
    fun getCarRequisition(@Body stringQuery: StringQuery): Single<Response<CarRequisition>>

    @POST("Car/CarList")
    fun getCarList(@Body pageRequest: PageRequest<CarListParam>): Single<PageResponse<Car>>

    @POST("Car/CarRequisitionApprove")
    fun approve(@Body carRequisition: CarRequisition): Single<Response<CarRequisition>>

    @POST("Car/CarRequisitionDeny")
    fun deny(@Body carRequisition: CarRequisition): Single<Response<CarRequisition>>

    @POST("Code/DictCarState")
    fun getCarState(): Single<Response<List<DictItem>>>

    @POST("Code/DictCarType")
    fun getCarType(): Single<Response<List<DictItem>>>

//    @POST("Code/DictRequisitionState")
//    fun getRequisitionState(): Observable<Response<List<DictItem>>>
//
//    @POST("Code/DictRequisitionFromType")
//    fun getRequisitionFromType(): Observable<Response<List<DictItem>>>
//
//    @POST("Code/DictRequisitionCause")
//    fun getRequisitionCause(): Observable<Response<List<DictItem>>>

    @POST("file/CheckAppVersion")
    fun checkAppVersion(@Body stringQuery: StringQuery): Single<Response<CheckAppVersionResponse>>

}
