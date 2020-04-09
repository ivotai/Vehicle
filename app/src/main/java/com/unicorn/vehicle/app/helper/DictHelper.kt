package com.unicorn.vehicle.app.helper

import com.unicorn.vehicle.app.di.ComponentHolder
import com.unicorn.vehicle.data.model.DictItem
import io.reactivex.schedulers.Schedulers

object DictHelper {

    var isInitFinish = false

    fun initDict() {
        with(api) {
            getCarState().flatMap {
                carStates = it.data
                getCarType()
            }.flatMap {
                carTypes = it.data
                getRequisitionState()
            }.flatMap {
                requisitionStates = it.data
                getRequisitionCause()
            }.flatMap {
                requisitionCauses = it.data
                getRequisitionFromType()
            }.subscribeOn(Schedulers.io()).subscribe {
                requisitionFromTypes = it.data
                isInitFinish = true
            }
        }
    }

    private val api by lazy { ComponentHolder.appComponent.simpleApi() }

    lateinit var carStates: List<DictItem>
    lateinit var carTypes: List<DictItem>
    lateinit var requisitionStates: List<DictItem>
    lateinit var requisitionFromTypes: List<DictItem>
    lateinit var requisitionCauses: List<DictItem>

}