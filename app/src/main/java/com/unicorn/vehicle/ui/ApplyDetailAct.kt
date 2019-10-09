package com.unicorn.vehicle.ui

import com.unicorn.vehicle.R
import com.unicorn.vehicle.ui.base.BaseAct


class ApplyDetailAct : BaseAct() {

    override val layoutId = R.layout.act_apply_detail
//
//    private val apply by lazy { intent.getSerializableExtra("Apply") as Apply }
//
//    override fun initViews() {
//        titleBar.setTitle("用车申请详情")
//
//        with(apply) {
//            tvPerson.text = person
//            tvReason.text = reason
//            tvStartTime.text = startTime
//            tvEndTime.text = endTime
//            tvDestination.text = destination
//            tvVehicleType.text = vehicleType
//        }
//    }
//
//    override fun bindIntent() {
//        tvVehicle.safeClicks().subscribe {
////            startAct(VehicleListAct::class.java)
//        }
//
//        rtvAgree.safeClicks().subscribe {
//            if (apply.vehicle == null) {
//                ToastUtils.showShort("请选择车辆")
//                return@subscribe
//            }
//            ToastUtils.showShort("已通过该申请")
//            finish()
//        }
//
//        rtvReject.safeClicks().subscribe {
//            MaterialDialog(this).show {
//                input(hint = "填写拒绝原因") { dialog, text ->
//                    ToastUtils.showShort("已拒绝该申请")
//                    finish()
//                }
//            }
//        }
//    }
//
//    override fun registerEvent() {
//        RxBus.registerEvent(this, Vehicle::class.java, Consumer {
//            apply.vehicle = it
//            tvVehicle.text = it.name
//        })
//    }

}