package com.unicorn.vehicle.ui.base

interface UI {

    val layoutId: Int

    fun inject()

    fun initViews()

    fun bindIntent()

    fun registerEvent()

}