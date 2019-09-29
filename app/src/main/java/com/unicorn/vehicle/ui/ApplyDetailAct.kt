package com.unicorn.vehicle.ui

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.app.startAct
import com.unicorn.vehicle.data.model.Apply
import com.unicorn.vehicle.data.model.Vehicle
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_apply_detail.*


class ApplyDetailAct : BaseAct() {

    override val layoutId = R.layout.act_apply_detail

    private val apply by lazy { intent.getSerializableExtra("Apply") as Apply }

    override fun initViews() {
        titleBar.setTitle("用车申请详情")

        with(apply) {
            tvPerson.text = person
            tvReason.text = reason
            tvStartTime.text = startTime
            tvEndTime.text = endTime
            tvDestination.text = destination
            tvVehicleType.text = vehicleType
        }
    }

    override fun bindIntent() {
        tvVehicle.safeClicks().subscribe {
            startAct(VehicleListAct::class.java)
        }

        rtvAgree.safeClicks().subscribe {
            if (apply.vehicle == null) {
                ToastUtils.showShort("请选择车辆")
                return@subscribe
            }
            ToastUtils.showShort("已通过该申请")
            finish()
        }

        rtvReject.safeClicks().subscribe {
            MaterialDialog(this).show {
                input(hint = "填写拒绝原因") { dialog, text ->
                    ToastUtils.showShort("已拒绝该申请")
                    finish()
                }
            }
        }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, Vehicle::class.java, Consumer {
            apply.vehicle = it
            tvVehicle.text = it.name
        })
    }

}