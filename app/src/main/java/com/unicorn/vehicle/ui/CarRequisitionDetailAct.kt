package com.unicorn.vehicle.ui

import android.content.Intent
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.helper.DialogHelper
import com.unicorn.vehicle.app.helper.EncryptionHelper
import com.unicorn.vehicle.data.model.Car
import com.unicorn.vehicle.data.model.CarRequisition
import com.unicorn.vehicle.data.model.param.StringQuery
import com.unicorn.vehicle.data.model.event.RefreshCarRequisitionList
import com.unicorn.vehicle.data.model.param.GeneralParam
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_car_requisition_detail.*
import org.joda.time.DateTime

class CarRequisitionDetailAct : BaseAct() {

    override fun initViews() {
        titleBar.setTitle("用车申请详情")
        getCarRequisition()
    }

    private fun getCarRequisition() {
        fun display() {
            with(carRequisition) {
                tvRequisitionUserName.text = requisitionUserName
                tvRequisitionServerTime.text =
                    DateTime(requisitionServerTime).toString(displayDateFormat2)
                tvRequisitionCauseDisplay.text = requisitionCauseDisplay
                tvRequisitionFromTypeDisplay.text = requisitionFromTypeDisplay
                tvRequisitionCarTypeDisplay.text = requisitionCarTypeDisplay
                tvRequisitionCarName.text = requisitionCarNo
                tvRequisitionStartTime.text =
                    DateTime(requisitionStartTime).toString(displayDateFormat2)
                tvRequisitionEndTime.text =
                    DateTime(requisitionEndTime).toString(displayDateFormat2)
                tvRequisitionDestination.text = requisitionDestination
                tvStateDisplay.text = stateDisplay
                tvApprovalUserName.text = approvalUserName
                tvApprovalRemark.text = approvalRemark
                tvApprovalServerTime.text =
                    DateTime(approvalServerTime).toString(displayDateFormat)
                llButton.visibility = if (state == 0) View.VISIBLE else View.GONE
            }
        }

        val mask = DialogHelper.showMask(this)
        val generalParam = GeneralParam.create(StringQuery(key = carRequisitionId))
        api.getCarRequisition(generalParam)
            .doOnSuccess {
                val json = EncryptionHelper.decrypt(it.encryptionData)
                it.data = json.toBean()
            }
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    carRequisition = it.data
                    display()
                },
                onError = {
                    mask.dismiss()
                }
            )
    }

    override fun bindIntent() {
        llRequisitionCarName.safeClicks().subscribe {
            Intent(this, CarSelectAct::class.java).apply {
                putExtra(Param, carRequisition)
            }.let { startActivity(it) }
        }

        rtvAgree.safeClicks().subscribe {
            if (carRequisition.requisitionCarID == null) {
                ToastUtils.showShort("请选择车辆")
                return@subscribe
            }
            approve()
        }

        rtvReject.safeClicks().subscribe {
            MaterialDialog(this).show {
                input(hint = "填写拒绝原因") { _, text ->
                    carRequisition.approvalRemark = text.toString()
                    deny()
                }
            }
        }
    }

    private fun approve() {
        val mask = DialogHelper.showMask(this)
        val generalParam = GeneralParam.create(carRequisition)
        api.approve(generalParam)
            .doOnSuccess {
                val json = EncryptionHelper.decrypt(it.encryptionData)
                it.data = json.toBean()
            }
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    ToastUtils.showShort("申请已通过")
                    RxBus.post(RefreshCarRequisitionList())
                    finish()
                },
                onError = {
                    mask.dismiss()
                }
            )
    }

    private fun deny() {
        val mask = DialogHelper.showMask(this)
        api.deny(carRequisition = carRequisition)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    ToastUtils.showShort("申请已拒绝")
                    RxBus.post(RefreshCarRequisitionList())
                    finish()
                },
                onError = {
                    mask.dismiss()
                }
            )
    }

    override fun registerEvent() {
        RxBus.registerEvent(this, Car::class.java, Consumer {
            carRequisition.requisitionCarID = it.id
            tvRequisitionCarName.text = it.no
        })
    }

    private lateinit var carRequisition: CarRequisition

    private val carRequisitionId by lazy { intent.getStringExtra(Param) }

    override val layoutId = R.layout.act_car_requisition_detail

}