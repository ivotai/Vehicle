package com.unicorn.vehicle.data.api

import com.unicorn.vehicle.data.model.*
import com.unicorn.vehicle.data.model.base.PageResponse
import com.unicorn.vehicle.data.model.base.Response
import com.unicorn.vehicle.data.model.param.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<Response<LoggedUser>>

    @POST("Car/CarRequisitionList")
    fun getCarRequisitionList(@Body generalParam: GeneralParam): Single<PageResponse<CarRequisition>>

    @POST("Car/CarRequisitionInfo")
    fun getCarRequisition(@Body generalParam: GeneralParam): Single<Response<CarRequisition>>

    @POST("Car/CarList")
    fun getCarList(@Body generalParam: GeneralParam): Single<PageResponse<Car>>

    @POST("Car/CarRequisitionApprove")
    fun approve(@Body generalParam: GeneralParam): Single<Response<CarRequisition>>

    @POST("Car/CarRequisitionDeny")
    fun deny(@Body generalParam: GeneralParam): Single<Response<CarRequisition>>

    @POST("Car/CarUsageLogList")
    fun getCarUsageLogList(@Body generalParam: GeneralParam): Single<PageResponse<CarUsageLog>>

    @POST("Code/DictCarState")
    fun getDictCarState(@Body generalParam: GeneralParam = GeneralParam.createForBasePostInfo()): Single<Response<List<DictItem>>>

    @POST("Code/DictCarType")
    fun getDictCarType(@Body generalParam: GeneralParam = GeneralParam.createForOrgParam()): Single<Response<List<DictItem>>>

    @POST("Code/DictCarUsageEventType")
    fun getDictCarUsageEventType(@Body generalParam: GeneralParam = GeneralParam.createForBasePostInfo()): Single<Response<List<DictItem>>>

    @POST("file/CheckAppVersion")
    fun checkAppVersion(@Body stringQuery: StringQuery): Single<Response<CheckAppVersionResponse>>

    @POST("Statistics/UsageCountForCar")
    fun getUsageCountForCar(@Body statisticCommonParam: StatisticCommonParam = StatisticCommonParam()): Observable<Response<List<StatisticCommonItem>>>

    @POST("Statistics/UsingHoursAverageForCar")
    fun getUsingHoursAverageForCar(@Body statisticCommonParam: StatisticCommonParam = StatisticCommonParam()): Observable<Response<List<StatisticCommonItem>>>

    @POST("Statistics/UsageCountForUser")
    fun getUsageCountForUser(@Body statisticCommonParam: StatisticCommonParam = StatisticCommonParam()): Observable<Response<List<StatisticCommonItem>>>

    @POST("Statistics/UsingHoursAverageForUser")
    fun getUsingHoursAverageForUser(@Body statisticCommonParam: StatisticCommonParam = StatisticCommonParam()): Observable<Response<List<StatisticCommonItem>>>

}
