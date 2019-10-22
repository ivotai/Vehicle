package com.unicorn.vehicle.ui

import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.helper.DialogHelper
import com.unicorn.vehicle.app.helper.DictHelper
import com.unicorn.vehicle.data.model.LoggedUser
import com.unicorn.vehicle.data.model.UserLoginParam
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : BaseAct() {

    override fun initViews() {
        etLoginStr.setText("admin")
        etUserPwd.setText("3.14159")
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
                    if (it.data.role != 1) {
                        ToastUtils.showShort("无法登录，您没有审批权限")
                        return@subscribeBy
                    }
                    Globals.loggedUser = it.data
                    Globals.userLoginParam = userLoginParam
                    startAct(CarRequisitionAct::class.java)
                    setAliasAndTags(it.data)
                },
                onError = {
                    mask.dismiss()
//                    ExceptionHelper.showPrompt(it)
                }
            )
    }

    override val layoutId = R.layout.act_login

    private fun setAliasAndTags(loggedUser: LoggedUser) {
        JPushInterface.setAliasAndTags(this, loggedUser.uid, setOf(loggedUser.role.toString())
        ) { p0, p1, p2 -> }
    }

}
