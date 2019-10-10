package com.unicorn.vehicle.ui

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.helper.DialogHelper
import com.unicorn.vehicle.app.helper.DictHelper
import com.unicorn.vehicle.data.model.CarListParam
import com.unicorn.vehicle.data.model.UserLoginParam
import com.unicorn.vehicle.data.model.base.PageRequest
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : BaseAct() {

    override fun initViews() {
        etLoginStr.setText("guiyang")
        etUserPwd.setText("123")
    }

    override fun bindIntent() {
        rtvLogin.safeClicks().subscribe { login() }
    }

    private fun login() {
        if (!DictHelper.isInitFinish) {
            ToastUtils.showShort("字典表初始化中")
            return
        }
        val mask = DialogHelper.showMask(this)
        val userLoginParam = UserLoginParam(
            loginStr = etLoginStr.trimText(),
            userPwd = EncryptUtils.encryptMD5ToString(etUserPwd.trimText())
        )

        api.login(userLoginParam = userLoginParam)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.failed) return@subscribeBy
                    Global.loggedUser = it.data
                    startAct(CarRequisitionAct::class.java)
//                    saveUserInfo()
//                    t()
                },
                onError = {
                    mask.dismiss()
//                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun t() {
        api.getCarList(PageRequest(pageNo = 1, searchParam = CarListParam()))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    if (it.failed) return@subscribeBy
                },
                onError = {
                }
            )
    }

    override val layoutId = R.layout.act_login

}
