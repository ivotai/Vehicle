package com.unicorn.vehicle.ui

import com.blankj.utilcode.util.EncryptUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.Global
import com.unicorn.vehicle.app.helper.DialogHelper
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.app.safeClicks
import com.unicorn.vehicle.app.trimText
import com.unicorn.vehicle.data.model.CarRequisitionListParam
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
//                    startAct(ApplyListAct::class.java)
//                    saveUserInfo()
                    test()
                },
                onError = {
                    mask.dismiss()
//                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    private fun test(){
        api.getCarRequisitionList(PageRequest(pageNo = 1,searchParam = CarRequisitionListParam()))
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    if (it.failed) return@subscribeBy

//                    saveUserInfo()
                },
                onError = {
//                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override val layoutId = R.layout.act_login

}
