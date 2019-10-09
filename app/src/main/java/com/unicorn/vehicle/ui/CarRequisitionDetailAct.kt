package com.unicorn.vehicle.ui

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.RxBus
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.app.startAct
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_car_requisition_detail.*

class CarRequisitionDetailAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("用车申请详情")
        with(carRequisition) {
            tvRequisitionUserName.text = requisitionUserName
            tvRequisitionServerTime.text = requisitionServerTime
            tvRequisitionCause.text = requisitionCause
            tvRequisitionFromTypeDisplay.text = requisitionFromTypeDisplay
            tvRequisitionCarTypeDisplay.text = requisitionCarTypeDisplay
            tvRequisitionCarName.text = requisitionCarName
            tvStateDisplay.text = stateDisplay
            tvApprovalUserName.text = approvalUserName
            tvApprovalRemark.text = approvalRemark
            tvApprovalServerTime.text = approvalServerTime
            llButton.visibility = if (state == 0) View.VISIBLE else View.GONE
        }
    }

    override fun bindIntent() {
        llRequisitionCarName.safeClicks().subscribe {
            startAct(CarListAct::class.java)
        }

        rtvAgree.safeClicks().subscribe {
            if (carRequisition.requisitionCarID == null) {
                ToastUtils.showShort("请选择车辆")
                return@subscribe
            }
            //
            ToastUtils.showShort("已通过该申请")
            finish()
        }

        rtvReject.safeClicks().subscribe {
            MaterialDialog(this).show {
                input(hint = "填写拒绝原因") { dialog, text ->
                    ToastUtils.showShort(text)
                    finish()
                }
            }
        }
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, Car::class.java, Consumer {
            carRequisition.requisitionCarID = it.id
            tvRequisitionCarName.text = it.name
        })
    }

    override val layoutId = R.layout.act_car_requisition_detail

    private val carRequisition by lazy { intent.getSerializableExtra("CarRequisition") as CarRequisition }

}