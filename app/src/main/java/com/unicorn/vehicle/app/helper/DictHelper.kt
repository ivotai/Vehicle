package com.unicorn.vehicle.app.helper

import com.unicorn.vehicle.app.di.ComponentHolder
import com.unicorn.vehicle.data.model.DictItem
import io.reactivex.schedulers.Schedulers

object DictHelper {

    fun initDict() {
        with(api) {
            getCarState().flatMap {
                carStates = it.data
                getCarType()
            }.flatMap {
                carTypes = it.data
                getDictRequisitionState()
            }.flatMap {
                requisitionStates = it.data
                getDictRequisitionFromType()
            }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    requisitionFromType = it.data
                }
        }
    }

    private val api by lazy { ComponentHolder.appComponent.api() }

    lateinit var carStates: List<DictItem>
    lateinit var carTypes: List<DictItem>
    lateinit var requisitionStates: List<DictItem>
    lateinit var requisitionFromType: List<DictItem>

}